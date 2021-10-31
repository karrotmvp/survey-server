package com.daangn.survey.third;

import com.daangn.survey.core.auth.oauth.AbstractUserDetail;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class KarrotUserDetail extends AbstractUserDetail {
    private int status;
    private KarrotUserData data;
    private String timestamp;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KarrotUserData {
        private String nickname;
        @JsonProperty("user_id")
        private String userId;
    }
}
