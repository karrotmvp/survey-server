package com.daangn.survey.domain.survey.service;

import com.daangn.survey.core.error.ErrorCode;
import com.daangn.survey.core.error.exception.BusinessException;
import com.daangn.survey.core.error.exception.EntityNotFoundException;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.question.model.dto.ChoiceDto;
import com.daangn.survey.domain.question.model.dto.QuestionDto;
import com.daangn.survey.domain.question.model.entity.Choice;
import com.daangn.survey.domain.question.model.entity.Question;
import com.daangn.survey.domain.question.model.entity.QuestionType;
import com.daangn.survey.domain.question.model.entity.QuestionTypeCode;
import com.daangn.survey.domain.question.model.mapper.ChoiceMapper;
import com.daangn.survey.domain.question.model.mapper.QuestionMapper;
import com.daangn.survey.domain.question.repository.ChoiceRepository;
import com.daangn.survey.domain.question.repository.QuestionRepository;
import com.daangn.survey.domain.question.service.QuestionService;
import com.daangn.survey.domain.survey.model.dto.SurveyBriefDto;
import com.daangn.survey.domain.survey.model.dto.SurveyDto;
import com.daangn.survey.domain.survey.model.dto.SurveySummaryDto;
import com.daangn.survey.domain.survey.model.entity.Survey;
import com.daangn.survey.domain.survey.model.mapper.SurveyMapper;
import com.daangn.survey.domain.survey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService{
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
    private final QuestionService questionService;

    private final SurveyMapper surveyMapper;
    private final QuestionMapper questionMapper;
    private final ChoiceMapper choiceMapper;

    @PreAuthorize("hasRole('ROLE_BIZ')")
    @Transactional
    public void saveSurvey(Member member, SurveyDto surveyDto){
        List<Question> questions = new LinkedList<>();
        List<Choice> choices = new LinkedList<>();

        Survey survey = surveyMapper.entityBuilder(surveyDto, member);

        for(int idx = 0; idx < surveyDto.getQuestions().size(); idx++){

            QuestionDto questionDto = surveyDto.getQuestions().get(idx);

            QuestionType questionType = questionService.findQuestionType(questionDto.getQuestionType());

            Question question = questionMapper.entityBuilder(questionDto, survey, idx, questionType);

            questions.add(question);

            if(!QuestionTypeCode.CHOICE_QUESTION.getNumber().equals(questionDto.getQuestionType()))
                continue;

            for (int number = 0; number < questionDto.getChoices().size(); number++) {
                ChoiceDto choiceDto = questionDto.getChoices().get(number);

                choices.add(choiceMapper.entityBuilder(choiceDto, question,  number));
            }

        }

        choiceRepository.saveAll(choices);
        questionRepository.saveAll(questions);
        surveyRepository.save(survey);
    }

    @Transactional(readOnly = true)
    public List<SurveySummaryDto> findAll(Long memberId){
        List<Survey> surveys = surveyRepository.findSurveysByMemberIdOrderByCreatedAtDesc(memberId);

        return surveys.stream().map(surveyMapper::toSummaryDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SurveySummaryDto> findSurveysByMemberId(Long memberId){
        List<Survey> surveys = surveyRepository.findSurveysByMemberIdOrderByCreatedAt(memberId);

        return surveys.stream().map(surveyMapper::toSummaryDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SurveyDto findBySurveyId(Long surveyId){
        Survey survey = surveyRepository.findById(surveyId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.SURVEY_NOT_FOUND));
        return surveyMapper.toDetailDto(survey);
    }

    @Transactional(readOnly = true)
    public SurveyBriefDto findSurveyBriefBySurveyId(Long surveyId){
        // Todo: SurveyBriefDto로 변환하기
        return null;
    }

    @Transactional
    public void deleteSurvey(Long surveyId, Long memberId){
        Survey survey = surveyRepository.findById(surveyId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.SURVEY_NOT_FOUND));

        if(!survey.isWriter(memberId))
            throw new BusinessException(ErrorCode.NOT_AUTHORIZED_FOR_DELETE);

        surveyRepository.delete(survey);
        // survey.delete();
    }
}
