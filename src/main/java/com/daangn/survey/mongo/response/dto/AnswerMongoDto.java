package com.daangn.survey.mongo.response.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerMongoDto {
    private Long questionId;
    private int questionType;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String text;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String choice;
}
