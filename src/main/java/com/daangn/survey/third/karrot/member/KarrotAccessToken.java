package com.daangn.survey.third.karrot.member;

import com.daangn.survey.core.auth.oauth.AbstractAccessToken;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KarrotAccessToken extends AbstractAccessToken {
    @JsonProperty(value = "access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType = "bearer";
    @JsonProperty("expires_in")
    private int expiresIn;
    private String scope;
}
