package com.daangn.survey.third.messaging.sqs;

import com.amazonaws.services.sqs.model.Message;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.groups.Default;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BizChatMessage extends Message {

    @Builder.Default
    private String type = "BIZ";
    private Sender sender;
    private Receiver receiver;
    private String title;
    private String content;
    private String image;

    @Getter @Setter
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
