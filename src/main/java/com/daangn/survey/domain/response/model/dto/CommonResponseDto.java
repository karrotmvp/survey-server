package com.daangn.survey.domain.response.model.dto;

import com.daangn.survey.domain.question.model.entity.QuestionType;
import com.fasterxml.jackson.annotation.JsonInclude;

public class CommonResponseDto {

    private int questionType;

    private Long questionId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String answer;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long choiceId;
}
