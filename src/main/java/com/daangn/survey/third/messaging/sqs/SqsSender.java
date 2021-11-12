package com.daangn.survey.third.messaging.sqs;

import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchResult;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface SqsSender {
    SendMessageResult sendMessage(ChatMessage message) throws JsonProcessingException;
    SendMessageBatchResult sendBatchMessage(List<ChatMessage> messages) throws JsonProcessingException;
}
