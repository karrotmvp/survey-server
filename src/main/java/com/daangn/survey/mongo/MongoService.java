package com.daangn.survey.mongo;

import com.daangn.survey.core.auth.oauth.SocialResolver;
import com.daangn.survey.core.error.ErrorCode;
import com.daangn.survey.core.error.exception.EntityNotFoundException;
import com.daangn.survey.domain.deprecated.aggregation.model.individual.SurveyResponsesBrief;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.member.model.mapper.MemberMapper;
import com.daangn.survey.domain.member.repository.MemberRepository;
import com.daangn.survey.domain.deprecated.survey.survey.model.dto.SurveyBriefDto;
import com.daangn.survey.domain.deprecated.survey.survey.model.mapper.SurveyMapper;
import com.daangn.survey.mongo.aggregate.AggregationQuestionMongo;
import com.daangn.survey.mongo.aggregate.individual.IndividualQuestionMongo;
import com.daangn.survey.mongo.common.SequenceGeneratorService;
import com.daangn.survey.mongo.response.ResponseMongo;
import com.daangn.survey.mongo.response.dto.ResponseMongoDto;
import com.daangn.survey.mongo.survey.QuestionMongo;
import com.daangn.survey.mongo.survey.SurveyMongo;
import com.daangn.survey.mongo.survey.SurveySummaryMongoDto;
import com.daangn.survey.mongo.survey.dto.SurveyMongoDto;
import com.daangn.survey.third.karrot.member.KarrotBizProfileDetail;
import lombok.RequiredArgsConstructor;
import org.dataloader.BatchLoader;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MongoService {
    private final MongoRepository mongoRepository;
    private final SequenceGeneratorService generatorService;
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final SurveyMapper surveyMapper;
    private final SocialResolver socialResolver;

    // Survey
    @Transactional
    public Long insertSurvey(SurveyMongo survey){
        return mongoRepository.insertSurvey(survey);
    }

    @Transactional(readOnly = true)
    public SurveyMongoDto findSurvey(Long surveyId){
        SurveyMongoDto survey = mongoRepository.getSurveyMongoDto(surveyId);

        survey.calculateCreatedAt();

        if(survey == null) throw new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND);

        return survey;
    }

    // 성능 최악인데 이걸 어떻게 해결할 수 있을까?
    // 1. bulk?
    // 2. data loader
    @Transactional(readOnly = true)
    public List<SurveySummaryMongoDto> findSurveysByMemberId(Long memberId) {

        List<SurveySummaryMongoDto> surveys = mongoRepository.findSurveysByMemberId(memberId);

        BatchLoader<SurveySummaryMongoDto, SurveySummaryMongoDto> userBatchLoader =
                surveys1 -> CompletableFuture.supplyAsync(() -> mongoRepository.getResponses(surveys1)
        );

        DataLoader<SurveySummaryMongoDto, SurveySummaryMongoDto> userLoader = DataLoaderFactory.newDataLoader(userBatchLoader);

        surveys.stream().map(userLoader::load).collect(Collectors.toList());

        userLoader.dispatchAndJoin();

        return surveys.stream()
                .map(SurveySummaryMongoDto::calculateCreatedAt)
                .sorted(Comparator.comparing(SurveySummaryMongoDto::getCreatedAt).reversed()).collect(Collectors.toList());


    }

    @Transactional(readOnly = true)
    public SurveyBriefDto findSurveyBriefBySurveyId(Long surveyId){

        SurveyMongo survey = mongoRepository.findSurveyMongo(surveyId);

        if(survey == null) throw new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND);

        Member member = memberRepository.findById(survey.getMemberId()).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        SurveyBriefDto surveyBriefDto = surveyMapper.toSurveyBriefDtoFromSurveyMongo(
                survey,
                memberMapper.toBizProfileDtoFromMember(member),
                survey.getSurveyEstimatedTime()
        );

        KarrotBizProfileDetail profile = socialResolver.resolveBizProfileDetails(member.getDaangnId());
        surveyBriefDto.setCoverImageUrls(profile.getCoverImageUrls());

        return surveyBriefDto;
    }

    @Transactional
    public void deleteSurvey(Long surveyId){
        mongoRepository.deleteSurvey(surveyId);
    }

    // Response
    @Transactional
    public void insertResponse(ResponseMongoDto response){
        Long responseId = generatorService.generateSequence(ResponseMongo.sequenceName);

        List<ResponseMongo> responses = response.getAnswers()
                .stream()
                .map(el -> ResponseMongo.builder()
                        .responseId(responseId)
                        .memberId(response.getMemberId())
                        .surveyId(response.getSurveyId())
                        .questionId(el.getQuestionId())
                        .value(el.getValue())
                        .createdAt(LocalDateTime.now().plusHours(9L))
                        .build())
                .collect(Collectors.toList());

        mongoRepository.insertResponses(responses);
    }

    // Aggregation
    @Transactional(readOnly = true)
    public List<AggregationQuestionMongo> getAggregation(Long surveyId){
        List<QuestionMongo> questions = mongoRepository.findSurveyMongo(surveyId).getQuestions();

        List<AggregationQuestionMongo> result = new LinkedList<>();

        for(int idx = 0 ; idx < questions.size(); idx++){
            QuestionMongo question = questions.get(idx);

            AggregationQuestionMongo aggregationQuestion = new AggregationQuestionMongo();
            aggregationQuestion.setQuestionType(question.getQuestionType());
            aggregationQuestion.setQuestion(question.getText());

            if(question.getQuestionType() == 2)
                aggregationQuestion.getAnswers().addAll(mongoRepository.getTextAnswers(surveyId, question.getId()));

            if(question.getQuestionType() == 3)
                aggregationQuestion.getAnswers().addAll(mongoRepository.getChoiceAnswers(surveyId, question.getId()));

            result.add(aggregationQuestion);
        }

        return result;
    }

    @Transactional(readOnly = true)
    public List<IndividualQuestionMongo> getIndividualResponseMongo(Long surveyId, Long responseId){
        SurveyMongoDto survey = mongoRepository.getSurveyMongoDto(surveyId);
        List<IndividualQuestionMongo> responses = survey.getQuestions()
                .stream()
                .map(el -> new IndividualQuestionMongo(el, mongoRepository.getResponse(el.getId(), responseId)))
                .collect(Collectors.toList());


        return responses;
    }

    @Transactional(readOnly = true)
    public SurveyResponsesBrief getResponseBrief(Long surveyId){
        List<Long> responseIds = mongoRepository.getResponseBrief(surveyId);

        return new SurveyResponsesBrief(responseIds.size(), responseIds);
    }

}
