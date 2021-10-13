package com.daangn.survey.third;

import com.daangn.survey.core.auth.oauth.SocialResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KarrotApiUtil implements SocialResolver {

    @Value("${karrot.openapi}")
    private String openApiUrl;

    @Value("${karrot.oapi}")
    private String oApiUrl;

    @Override
    public KarrotUserDetail resolveUserDetails() {
        return null;
    }

    @Override
    public String resolveAccessToken() {
        return webClient.mutate()
                .baseUrl("https://some.com/api")
                .build()
                .get()
                .uri("/resource?id={ID}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(SomeData.class)
                ;
    }
}
