package com.daangn.survey.third.karrot.chatting;

import com.amazonaws.services.sqs.model.Message;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserChatMessage extends Message {
    private String type = "USER";
    private InputMessage input;

    public UserChatMessage(InputMessage inputMessage){
        input = inputMessage;
    }

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InputMessage {
        private List<Action> actions;
        private String userId;
        private String title;
        private String text;
        private String imageUrl;

        @Getter @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Action {
            private Payload payload;
            private String type;

            @Getter @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Payload {
                private String text;
                private String linkUrl;
            }
        }
    }
}
