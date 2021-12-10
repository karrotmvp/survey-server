package com.daangn.survey.domain.deprecated.survey.survey.service;

import com.daangn.survey.core.auth.oauth.SocialResolver;
import com.daangn.survey.core.error.ErrorCode;
import com.daangn.survey.core.error.exception.BusinessException;
import com.daangn.survey.core.error.exception.EntityNotFoundException;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.member.model.mapper.MemberMapper;
import com.daangn.survey.domain.deprecated.survey.question.model.entity.Choice;
import com.daangn.survey.domain.deprecated.survey.question.model.entity.Question;
import com.daangn.survey.domain.deprecated.survey.question.repository.ChoiceRepository;
import com.daangn.survey.domain.deprecated.survey.question.repository.QuestionRepository;
import com.daangn.survey.domain.deprecated.survey.survey.model.dto.SurveyBriefDto;
import com.daangn.survey.domain.deprecated.survey.survey.model.dto.SurveyDto;
import com.daangn.survey.domain.deprecated.survey.survey.model.dto.SurveyRequestDto;
import com.daangn.survey.domain.deprecated.survey.survey.model.dto.SurveySummaryDto;
import com.daangn.survey.domain.deprecated.survey.survey.model.entity.Survey;
import com.daangn.survey.domain.deprecated.survey.survey.model.mapper.SurveyMapper;
import com.daangn.survey.domain.deprecated.survey.survey.repository.SurveyRepository;
import com.daangn.survey.third.karrot.member.KarrotBizProfileDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;

    private final SurveyMapper surveyMapper;
    private final MemberMapper memberMapper;
    private final SocialResolver socialResolver;


    @Transactional
    public Long saveSurvey(Member member, SurveyRequestDto surveyRequestDto) {
        Survey survey = surveyMapper.toEntity(surveyRequestDto, member);

        List<Question> questions = survey.getQuestions()
                                        .stream()
                                        .map(el -> el.setSurvey(survey))
                                        .collect(Collectors.toList());

        questions.forEach(el-> el.setOrder(questions.indexOf(el)));

        List<Choice> choices = survey.getQuestions()
                .stream()
                .filter(el -> el.getChoices() != null)
                .flatMap(el -> el.getChoices().parallelStream().map(el2-> el2.setQuestion(el)))
                .collect(Collectors.toList());

        choices.forEach(el -> el.setOrder(choices.indexOf(el)));

        choiceRepository.saveAll(choices);
        questionRepository.saveAll(questions);
        surveyRepository.save(survey);

        return survey.getId();
    }

    @Transactional(readOnly = true)
    public List<SurveySummaryDto> findSurveysByMemberId(Long memberId){
        List<Survey> surveys = surveyRepository.findSurveysByMemberIdOrderByCreatedAtDesc(memberId);

        return surveys.stream().map(surveyMapper::toSummaryDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SurveyDto findBySurveyId(Long surveyId){

        return surveyMapper.toDetailDto(findSurvey(surveyId));
    }

    /**
     * TODO
     * 비즈프로필 정보를 계속 요청하는 형태
     */
    @Transactional(readOnly = true)
    public SurveyBriefDto findSurveyBriefBySurveyId(Long surveyId){
        Survey survey = findSurvey(surveyId);

        SurveyBriefDto surveyBriefDto = surveyMapper.toSurveyBriefDtoWithMember(
                                            survey,
                                            memberMapper.toBizProfileDtoFromMember(survey.getMember()),
                                            survey.getSurveyEstimatedTime()
                                        );

        KarrotBizProfileDetail profile = socialResolver.resolveBizProfileDetails(survey.getMember().getDaangnId());
        surveyBriefDto.setCoverImageUrls(profile.getCoverImageUrls());

        return surveyBriefDto;
    }

    @Transactional
    public void deleteSurvey(Long surveyId, Long memberId){
        Survey survey = findSurvey(surveyId);

        if(!survey.isWriter(memberId))
            throw new BusinessException(ErrorCode.NOT_AUTHORIZED_FOR_DELETE);

        surveyRepository.delete(survey);
    }

    @Transactional
    public void patchSurveyAboutPublishing(Long surveyId){
        Survey survey = findSurvey(surveyId);

        survey.patchAboutPublishing();
    }

    private Survey findSurvey(Long surveyId){
        return surveyRepository.findById(surveyId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.SURVEY_NOT_FOUND));
    }

}
