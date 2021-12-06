package com.daangn.survey.mongo.survey.dto;

import lombok.Getter;

import java.util.List;

/**
 * Not Used, Just Used for Swagger
 */
@Getter
public class SurveyMongoRequestDto {
    private String title;
    private int target;
    private List<QuestionMongoRequestDto> questions;

    @Getter
    public class QuestionMongoRequestDto{
        private int questionType;
        private String text;
        private String description;
        private List<ChoiceMongoDto> choices;
    }

}
