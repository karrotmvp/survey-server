package com.daangn.survey.core.auth.oauth;

import com.daangn.survey.core.auth.jwt.model.AccessToken;
import com.daangn.survey.third.KarrotAccessToken;

public interface SocialResolver<T> {
    <T extends UserDetail> T resolveUserDetails(String accessToken);
    <T extends AccessToken> T resolveAccessToken(String code);
    <T extends UserDetail> T resolveBizProfileDetails(String code);
}
