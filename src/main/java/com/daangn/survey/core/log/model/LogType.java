package com.daangn.survey.core.log.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LogType {
    SHORT_URL("ShortUrl"),
    ;

    private String type;
}
