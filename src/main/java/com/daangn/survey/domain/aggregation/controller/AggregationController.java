package com.daangn.survey.domain.aggregation.controller;

import com.daangn.survey.domain.aggregation.model.Aggregatable;
import com.daangn.survey.domain.aggregation.model.ChoiceResponseAggregation;
import com.daangn.survey.domain.aggregation.model.SurveyAggregation;
import com.daangn.survey.domain.aggregation.repository.AggregateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/aggregation")
@RestController
public class AggregationController {

    private final AggregateRepository aggregateRepository;

    @GetMapping("/{surveyId}")
    public SurveyAggregation getAggregation(@PathVariable Long surveyId){
        List<Aggregatable> aggregatableList = new LinkedList<>();
        List<ChoiceResponseAggregation> choiceResponseAggregations = aggregateRepository.getChoice(surveyId);
        aggregatableList.addAll(choiceResponseAggregations);

        SurveyAggregation surveyAggregation = new SurveyAggregation(surveyId, aggregatableList);

        return surveyAggregation;

    }
}
