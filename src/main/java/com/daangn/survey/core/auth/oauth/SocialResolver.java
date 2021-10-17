package com.daangn.survey.core.auth.oauth;

import com.daangn.survey.core.auth.jwt.model.AbstractAccessToken;

public interface SocialResolver {
    <T extends AbstractUserDetail> T resolveUserDetails(String accessToken);
    <T extends AbstractAccessToken> T resolveAccessToken(String code);
    <T extends AbstractUserDetail> T resolveBizProfileDetails(String code);
}
