package com.daangn.survey.mongo.aggregate;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SurveyResponseCountMongo {
    private Long surveyId;
    private int count;
}
