package com.daangn.survey.domain.deprecated.aggregation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TextResponseAggregation implements Aggregatable {
    private Long surveyResponseId;
    private String answer;
}
