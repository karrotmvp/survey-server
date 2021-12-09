package com.daangn.survey.core.log.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@Getter @Setter
@Document(collection = "userLogs")
public class ShortUrlLog {
    @Enumerated(EnumType.STRING)
    private LogType logType;
    private Long surveyId;
    private String url;
    private String userAgent;
    private String referer;

    public ShortUrlLog(Long surveyId, String url, String userAgent, String referer) {
        this.logType = LogType.SHORT_URL;
        this.surveyId = surveyId;
        this.url = url;
        this.userAgent = userAgent;
        this.referer = referer;
    }
}
