package com.daangn.survey.domain.question.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum QuestionTypeCode {
    TEXT_QUESTION(1L), CHOICE_QUESTION(2L)
    ;

    private final Long number;
}
