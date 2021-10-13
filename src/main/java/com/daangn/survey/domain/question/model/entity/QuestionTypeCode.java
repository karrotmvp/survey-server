package com.daangn.survey.domain.question.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum QuestionTypeCode {
    INTRODUCE(1L), TEXT_QUESTION(2L), CHOICE_QUESTION(3L)
    ;

    private final Long number;
}
