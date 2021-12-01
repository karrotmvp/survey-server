package com.daangn.survey.third.messaging.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Override
    public SendMessageBatchResult sendBatchMessage(List<Message> messages) {
        List<SendMessageBatchRequestEntry> batchRequestEntries = messages.stream().map(el ->
        {
            try {
                return new SendMessageBatchRequestEntry(UUID.randomUUID() + "" + System.nanoTime(), objectMapper.writeValueAsString(el));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }
      ).collect(Collectors.toList());

        SendMessageBatchRequest sendBatchRequest = new SendMessageBatchRequest()
                .withQueueUrl(properties.getUrl())
                .withEntries(batchRequestEntries);

        return amazonSQS.sendMessageBatch(sendBatchRequest);
    }
}
