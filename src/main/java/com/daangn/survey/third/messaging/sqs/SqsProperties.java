package com.daangn.survey.third.messaging.sqs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "cloud.aws.sqs.queue")
public class SqsProperties {
    private String url;
}