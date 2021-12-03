package com.daangn.survey.mongo.aggregate;

import lombok.Getter;

public abstract class AggregationAnswerMongo {

    @Getter
    public class TextAnswerMongo extends AggregationAnswerMongo {
        private Long responseId;
        private String text;
    }

    @Getter
    public class ChoiceAnswerMongo extends AggregationAnswerMongo {
        private String choice;
        private int count;
    }
}
