package com.daangn.survey.core.log.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter @Setter
@NoArgsConstructor
@Document(collection = "userLogs")
public class ShortUrlLog {
    @Enumerated(EnumType.STRING)
    private LogType logType;
    private Long surveyId;
    private String url;
    private String userAgent;
    private String referrer;
    private LocalDateTime createdAt;

    public ShortUrlLog(Long surveyId, String url, String userAgent, String referrer) {
        this.logType = LogType.SHORT_URL;
        this.surveyId = surveyId;
        this.url = url;
        this.userAgent = userAgent;
        this.referrer = referrer;
        this.createdAt = LocalDateTime.now().plusHours(9L);
    }
}
