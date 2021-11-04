package com.daangn.survey.third.messaging.sqs;

import com.amazonaws.services.sqs.model.Message;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ChatMessage extends Message {

    private Sender sender;
    private Receiver receiver;
    private String title;
    private String content;
    private String image;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Receiver {
        private int id;
        private String type;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Sender {
        private int id;
        private String type;
    }
}
