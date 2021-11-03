package com.daangn.survey.third.messaging.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SqsSenderImpl implements SqsSender{
    private final ObjectMapper objectMapper;
    private final AmazonSQS amazonSQS;
    private final SqsProperties properties;

    @Override
    public SendMessageResult sendMessage(Message msg) throws JsonProcessingException {
        SendMessageRequest sendMessageRequest = new SendMessageRequest(properties.getUrl(), objectMapper.writeValueAsString(msg));
        return amazonSQS.sendMessage(sendMessageRequest);
    }
}
