package com.daangn.survey.third;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class KarrotAccessToken {
    private String accessToken;
    private String tokenType = "bearer";
    private int expiresIn;
    private String scope;
}
