package com.daangn.survey.domain.deprecated.aggregation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyAggregation {
    private Long surveyId;
    private List<QuestionAggregation> questionAggregations;

}
