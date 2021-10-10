package com.daangn.survey.domain.survey.service;

import com.daangn.survey.domain.survey.model.dto.ChoiceDto;
import com.daangn.survey.domain.survey.model.dto.QuestionDto;
import com.daangn.survey.domain.survey.model.dto.SurveyDto;
import com.daangn.survey.domain.survey.model.entity.Choice;
import com.daangn.survey.domain.survey.model.entity.Question;
import com.daangn.survey.domain.survey.model.entity.Survey;
import com.daangn.survey.domain.survey.model.mapper.ChoiceMapper;
import com.daangn.survey.domain.survey.model.mapper.QuestionMapper;
import com.daangn.survey.domain.survey.model.mapper.SurveyMapper;
import com.daangn.survey.domain.survey.repository.ChoiceRepository;
import com.daangn.survey.domain.survey.repository.QuestionRepository;
import com.daangn.survey.domain.survey.repository.QuestionTypeRepository;
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

            questions.add(questionMapper.entityBuilder(questionDto, idx, questionTypeRepository.findById(questionDto.getQuestionType()).get()));

            if(questionDto.getQuestionType() == 2) { // TODO : Enum으로 변경하기
                for (int number = 0; number < questionDto.getChoiceList().size(); number++) {
                    ChoiceDto choiceDto = questionDto.getChoiceList().get(number);

                    choices.add(choiceMapper.entityBuilder(choiceDto, number));
                }
            }
        }

        surveyRepository.save(survey);
        questionRepository.saveAll(questions);
        choiceRepository.saveAll(choices);
    }
}
