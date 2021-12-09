package com.daangn.survey.common.util.shorturl.model.entity;

import com.daangn.survey.common.util.log.LogType;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "logs")
public class ShortUrlLog {
    private LogType logType;
    private String url;
    private String userAgent;
    private String referer;

    public void setLogType(){
        this.logType = LogType.ShortUrl;
    }
}
