package com.daangn.survey.domain.survey.service;

import com.daangn.survey.domain.question.model.dto.ChoiceDto;
import com.daangn.survey.domain.question.model.dto.QuestionDto;
import com.daangn.survey.domain.survey.model.dto.SurveyDto;
import com.daangn.survey.domain.question.model.entity.Choice;
import com.daangn.survey.domain.question.model.entity.Question;
import com.daangn.survey.domain.question.model.entity.QuestionTypeCode;
import com.daangn.survey.domain.survey.model.entity.Survey;
import com.daangn.survey.domain.question.model.mapper.ChoiceMapper;
import com.daangn.survey.domain.question.model.mapper.QuestionMapper;
import com.daangn.survey.domain.survey.model.mapper.SurveyMapper;
import com.daangn.survey.domain.question.repository.ChoiceRepository;
import com.daangn.survey.domain.question.repository.QuestionRepository;
import com.daangn.survey.domain.question.repository.QuestionTypeRepository;
import com.daangn.survey.domain.survey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final QuestionTypeRepository questionTypeRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;

    private final SurveyMapper surveyMapper;
    private final QuestionMapper questionMapper;
    private final ChoiceMapper choiceMapper;

    @Transactional
    public void saveSurvey(SurveyDto surveyDto){
        List<Question> questions = new LinkedList<>();
        List<Choice> choices = new LinkedList<>();

        Survey survey = surveyMapper.entityBuilder(surveyDto);

        for(int idx = 0; idx < surveyDto.getQuestionList().size(); idx++){

            QuestionDto questionDto = surveyDto.getQuestionList().get(idx);

            Question question = questionMapper.entityBuilder(questionDto, survey, idx, questionTypeRepository.findById(questionDto.getQuestionType()).get());

            questions.add(question);

            if(QuestionTypeCode.CHOICE_QUESTION.getNumber().equals(questionDto.getQuestionType())) {

                for (int number = 0; number < questionDto.getChoiceList().size(); number++) {
                    ChoiceDto choiceDto = questionDto.getChoiceList().get(number);

                    choices.add(choiceMapper.entityBuilder(choiceDto, question,  number));
                }
            }
        }

        surveyRepository.save(survey);
        questionRepository.saveAll(questions);
        choiceRepository.saveAll(choices);
    }
}
