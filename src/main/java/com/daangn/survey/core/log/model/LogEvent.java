package com.daangn.survey.core.log.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LogEvent {
    private String url;
    private String userAgent;
    private String referer;
}
