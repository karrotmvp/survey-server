package com.daangn.survey.core.auth.oauth;

import com.daangn.survey.third.KarrotUserDetail;

public interface SocialResolver {
    KarrotUserDetail resolveUserDetails();
    String resolveAccessToken();
}
