package com.daangn.survey.domain.survey.service;

import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.question.model.entity.Choice;
import com.daangn.survey.domain.question.model.entity.Question;
import com.daangn.survey.domain.question.model.mapper.ChoiceMapper;
import com.daangn.survey.domain.question.model.mapper.QuestionMapper;
import com.daangn.survey.domain.question.repository.ChoiceRepository;
import com.daangn.survey.domain.question.repository.QuestionRepository;
import com.daangn.survey.domain.question.repository.QuestionTypeRepository;
import com.daangn.survey.domain.survey.model.dto.SurveyDto;
import com.daangn.survey.domain.survey.model.entity.Survey;
import com.daangn.survey.domain.survey.model.mapper.SurveyMapper;
import com.daangn.survey.domain.survey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

//@Service
@RequiredArgsConstructor
public class SurveyServiceImplV2 implements SurveyService {
    private final SurveyRepository surveyRepository;
    private final ChoiceRepository choiceRepository;
    private final QuestionRepository questionRepository;

    private final SurveyMapper surveyMapper;

    @Transactional
    public void saveSurvey(Member member, SurveyDto surveyDto) {
        Survey survey = surveyMapper.toEntity(surveyDto, member);

        List<Choice> choices = survey.getQuestions()
                .stream()
                .flatMap(el -> el.getChoices().parallelStream())
                .collect(Collectors.toList());

        List<Question> questions = survey.getQuestions().stream().collect(Collectors.toList());

        choiceRepository.saveAll(choices);
        questionRepository.saveAll(questions);
        surveyRepository.save(survey);
    }
}