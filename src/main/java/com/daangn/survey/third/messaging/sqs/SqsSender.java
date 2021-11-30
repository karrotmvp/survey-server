package com.daangn.survey.third.messaging.sqs;

import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageBatchResult;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.daangn.survey.third.karrot.chatting.UserChatMessage;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface SqsSender {
    SendMessageResult sendMessage(Message message) throws JsonProcessingException;
    SendMessageBatchResult sendBatchMessage(List<Message> messages) throws JsonProcessingException;
}
