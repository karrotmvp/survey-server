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
    private String value;
    private Long count;

    /**
     * "questionId" : int,
     * 		"choices" : [{
     * 			"choiceId" : int,
     * 			"value" : String,
     * 			"count" : int
     */
}
