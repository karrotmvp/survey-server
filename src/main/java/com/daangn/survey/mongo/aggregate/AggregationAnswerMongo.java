package com.daangn.survey.mongo.aggregate;

import lombok.Getter;

public abstract class AggregationAnswerMongo {

    @Getter
    public class TextAnswerMongo extends AggregationAnswerMongo {
        private Long responseId;
        private String value;
    }

    @Getter
    public class ChoiceAnswerMongo extends AggregationAnswerMongo {
        private String value;
        private int count;
    }
}
