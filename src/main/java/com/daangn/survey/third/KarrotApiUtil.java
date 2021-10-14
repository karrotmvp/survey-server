package com.daangn.survey.third;

import com.daangn.survey.core.auth.oauth.SocialResolver;
import com.daangn.survey.core.config.RestTemplateConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KarrotApiUtil implements SocialResolver {

    @Value("${karrot.openapi}")
    private String openApiUrl;

    @Value("${karrot.oapi}")
    private String oApiUrl;

    private final RestTemplate restTemplate;

    @Override
    public KarrotUserDetail resolveUserDetails() {
        return null;
    }

    @Override
    public String resolveAccessToken() {
        return null;
    }
}
