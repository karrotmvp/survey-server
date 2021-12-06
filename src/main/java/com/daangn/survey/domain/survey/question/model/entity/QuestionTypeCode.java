package com.daangn.survey.domain.survey.question.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum QuestionTypeCode {
    INTRODUCE(1L, "소개"), TEXT_QUESTION(2L, "단답주관식"), CHOICE_QUESTION(3L, "단일객관식")
    ;

    private final Long number;
    private final String korean;

    public static QuestionTypeCode findByNumber(Long number){
        return Arrays.stream(QuestionTypeCode.values())
                .filter(el -> el.getNumber().equals(number))
                .findFirst()
                .get();
    }
}
