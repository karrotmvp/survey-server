package com.daangn.survey.domain.aggregation.service;

import com.daangn.survey.domain.aggregation.model.Aggregatable;
import com.daangn.survey.domain.aggregation.model.QuestionAggregation;
import com.daangn.survey.domain.aggregation.model.SurveyAggregation;
import com.daangn.survey.domain.aggregation.repository.AggregateRepository;
import com.daangn.survey.domain.question.model.entity.QuestionTypeCode;
import com.daangn.survey.domain.survey.model.entity.Survey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AggregationService {

    private final AggregateRepository aggregateRepository;

    @Transactional(readOnly = true)
    public SurveyAggregation getSurveyAggregations(Long surveyId){

        List<QuestionAggregation> questions = aggregateRepository.getQuestions(surveyId);

        questions.forEach(el -> {
            if(QuestionTypeCode.CHOICE_QUESTION.getNumber() == el.getQuestionType())
                el.setChoiceAnswers(aggregateRepository.getChoiceAggregation(el.getQuestionId()));
            else if(QuestionTypeCode.TEXT_QUESTION.getNumber() == el.getQuestionType())
                el.setTextAnswers(aggregateRepository.getTextAggregation(el.getQuestionId()));
        });

        return new SurveyAggregation(surveyId, questions);
    }
}
