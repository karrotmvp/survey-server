package com.daangn.survey.mongo;

import com.daangn.survey.domain.aggregation.model.individual.SurveyResponsesBrief;
import com.daangn.survey.domain.survey.survey.model.dto.SurveySummaryDto;
import com.daangn.survey.mongo.aggregate.AggregationQuestionMongo;
import com.daangn.survey.mongo.aggregate.individual.IndividualQuestionMongo;
import com.daangn.survey.mongo.common.SequenceGeneratorService;
import com.daangn.survey.mongo.response.ResponseMongo;
import com.daangn.survey.mongo.response.dto.ResponseMongoDto;
import com.daangn.survey.mongo.survey.QuestionMongo;
import com.daangn.survey.mongo.survey.SurveyMongo;
import com.daangn.survey.mongo.survey.SurveySummaryMongoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MongoService {
    private final MongoRepository mongoRepository;
    private final SequenceGeneratorService generatorService;

    // Survey
    @Transactional
    public void insertSurvey(SurveyMongo survey){
        mongoRepository.insertSurvey(survey);
    }

    @Transactional(readOnly = true)
    public SurveyMongo findSurvey(Long surveyId){
        return mongoRepository.getSurveyMongo(surveyId);
    }

    @Transactional(readOnly = true)
    public List<SurveySummaryMongoDto> findSurveysByMemberId(Long memberId){

        return mongoRepository.findSurveysByMemberId(memberId)
                            .stream()
                            .map(el -> el.setResponseCount(mongoRepository.getResponseBrief(el.getId()).size()))
                            .collect(Collectors.toList());
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
                        .questionType(el.getQuestionType())
                        .choice(el.getChoice())
                        .text(el.getText())
                        .build())
                .collect(Collectors.toList());

        mongoRepository.insertResponses(responses);
    }

    // Aggregation
    @Transactional(readOnly = true)
    public List<AggregationQuestionMongo> getAggregation(Long surveyId){
        List<QuestionMongo> questions = mongoRepository.getSurveyMongo(surveyId).getQuestions();

        List<AggregationQuestionMongo> result = new LinkedList<>();

        for(int idx = 0 ; idx < questions.size(); idx++){
            QuestionMongo question = questions.get(idx);

            AggregationQuestionMongo aggregationQuestion = new AggregationQuestionMongo();
            aggregationQuestion.setQuestionType(question.getQuestionType());
            aggregationQuestion.setQuestion(question.getText());

            if(question.getQuestionType() == 2)
                aggregationQuestion.getAnswers().addAll(mongoRepository.getTextAnswers(question.getId()));

            if(question.getQuestionType() == 3)
                aggregationQuestion.getAnswers().addAll(mongoRepository.getChoiceAnswers(question.getId()));

            result.add(aggregationQuestion);
        }

        return result;
    }

    @Transactional(readOnly = true)
    public List<IndividualQuestionMongo> getIndividualResponseMongo(Long surveyId, Long responseId){
        SurveyMongo survey = mongoRepository.getSurveyMongo(surveyId);
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
