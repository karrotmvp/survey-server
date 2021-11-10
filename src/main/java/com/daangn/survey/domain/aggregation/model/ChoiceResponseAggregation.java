package com.daangn.survey.domain.aggregation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceResponseAggregation implements Aggregatable {
    private Long surveyId;
    private String question;
    private int order;
    private String value;
    private long count;

    /**
     * "questionId" : int,
     * 		"choices" : [{
     * 			"choiceId" : int,
     * 			"value" : String,
     * 			"count" : int
     */
}
