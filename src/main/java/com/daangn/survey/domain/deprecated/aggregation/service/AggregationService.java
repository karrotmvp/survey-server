package com.daangn.survey.domain.deprecated.aggregation.service;

import com.daangn.survey.domain.deprecated.aggregation.model.individual.QuestionResponseDto;
import com.daangn.survey.core.error.ErrorCode;
import com.daangn.survey.core.error.exception.EntityNotFoundException;
import com.daangn.survey.domain.deprecated.aggregation.model.QuestionAggregation;
import com.daangn.survey.domain.deprecated.aggregation.model.SurveyAggregation;
import com.daangn.survey.domain.deprecated.aggregation.model.individual.IndividualResponseDetailDto;
import com.daangn.survey.domain.deprecated.aggregation.model.individual.SurveyResponsesBrief;
import com.daangn.survey.domain.deprecated.aggregation.repository.AggregateRepository;
import com.daangn.survey.domain.deprecated.survey.question.model.entity.Question;
import com.daangn.survey.domain.deprecated.survey.question.model.entity.QuestionTypeCode;
import com.daangn.survey.domain.deprecated.survey.question.model.mapper.ChoiceMapper;
import com.daangn.survey.domain.deprecated.response.model.entity.ChoiceResponse;
import com.daangn.survey.domain.deprecated.response.model.entity.SurveyResponse;
import com.daangn.survey.domain.deprecated.response.model.entity.TextResponse;
import com.daangn.survey.domain.deprecated.response.model.mapper.ResponseMapper;
import com.daangn.survey.domain.deprecated.response.repository.ChoiceResponseRepository;
import com.daangn.survey.domain.deprecated.response.repository.TextResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AggregationService {

    private final AggregateRepository aggregateRepository;
    private final TextResponseRepository textResponseRepository;
    private final ChoiceResponseRepository choiceResponseRepository;
    private final ResponseMapper responseMapper;
    private final ChoiceMapper choiceMapper;

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

    @Transactional(readOnly = true)
    public SurveyResponsesBrief getSurveyResponsesBrief(Long surveyId){
        SurveyResponsesBrief brief = SurveyResponsesBrief.builder()
                                                        .responseIds(aggregateRepository.getSurveyResponseIds(surveyId))
                                                        .build();
        brief.setCount(brief.getResponseIds().size());

        return brief;
    }

    @Transactional(readOnly = true)
    public List<IndividualResponseDetailDto> getIndividualSurveyResponse(SurveyResponse surveyResponse){

        List<IndividualResponseDetailDto> adminResponses = new LinkedList<>();

        for(Question question : surveyResponse.getSurvey().getQuestions()){
            IndividualResponseDetailDto individualResponseDetailDto = IndividualResponseDetailDto.builder().build();

            QuestionResponseDto questionResponse = QuestionResponseDto.builder().text(question.getText()).questionType(question.getQuestionType().getId()).build();

            individualResponseDetailDto.setQuestion(questionResponse);

            switch(QuestionTypeCode.findByNumber(question.getQuestionType().getId())){
                case TEXT_QUESTION:

                    TextResponse textResponse = textResponseRepository.findTextResponseByQuestionIdAndSurveyResponseId(question.getId(), surveyResponse.getId())
                            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
                    individualResponseDetailDto.setResponse(responseMapper.toDtoFromTextResponse(textResponse));
                    break;

                case CHOICE_QUESTION:

                    questionResponse.setChoices(question.getChoices().stream().map(choiceMapper::toChoiceDto).collect(Collectors.toList()));

                    ChoiceResponse choiceResponse = choiceResponseRepository.findChoiceResponseByQuestionIdAndSurveyResponseId(question.getId(), surveyResponse.getId())
                            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
                    individualResponseDetailDto.setResponse(responseMapper.toDtoFromChoiceResponse(choiceResponse));
                    break;
            }

            adminResponses.add(individualResponseDetailDto);
        }

        return adminResponses;

    }
}
