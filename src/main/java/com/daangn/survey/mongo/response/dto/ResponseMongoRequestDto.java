package com.daangn.survey.mongo.response.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

/**
 * Not Used, Just Used for Swagger
 */
@Getter
public class ResponseMongoRequestDto {
    private Long surveyId;

    private List<AnswerMongoRequestDto> answers;

    @Getter
    public class AnswerMongoRequestDto {
        private Long questionId;
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private String text;
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private String choice;
    }

}
