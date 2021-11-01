package com.daangn.survey.domain.survey.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WriterType {
    USER("일반", "1"), BIZ("비즈니스", "2");

    private String value;
    private String code;
}
