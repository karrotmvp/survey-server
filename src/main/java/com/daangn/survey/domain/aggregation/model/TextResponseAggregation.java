package com.daangn.survey.domain.aggregation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TextResponseAggregation implements Aggregatable{
    private Long questionId;
    private String question;
    private int order;
    private List<String> texts;
}
