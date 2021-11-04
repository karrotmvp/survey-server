package com.daangn.survey.third.messaging.sqs;

import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface SqsSender {
    SendMessageResult sendMessage(ChatMessage message) throws JsonProcessingException;
}
