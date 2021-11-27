package com.daangn.survey.mongo.aggregate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AggregationQuestionMongo {
    private String question;
    private int questionType;
    private List<AggregationMongo> answers;
}
