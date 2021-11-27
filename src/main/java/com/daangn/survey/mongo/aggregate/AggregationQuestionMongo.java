package com.daangn.survey.mongo.aggregate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AggregationQuestionMongo {
    private String question;
    private int questionType;
    private AggregationListMongo answers;
}
