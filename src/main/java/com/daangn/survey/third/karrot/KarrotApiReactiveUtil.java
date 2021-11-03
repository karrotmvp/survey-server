package com.daangn.survey.third.karrot;

import com.daangn.survey.core.auth.jwt.model.AbstractAccessToken;
import com.daangn.survey.core.auth.oauth.SocialResolver;
import com.daangn.survey.core.error.ErrorCode;
import com.daangn.survey.core.error.exception.KarrotAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

//@Component
@RequiredArgsConstructor
public class KarrotApiReactiveUtil implements SocialResolver {

    @Value("${karrot.openapi}")
    private String openApiUrl;

    @Value("${karrot.oapi}")
    private String oApiUrl;

    @Value("${karrot.app.id}")
    private String appId;

    @Value("${karrot.app.secret}")
    private String appSecret;

    @Value("${karrot.app.apikey}")
    private String appApiKey;

    private final WebClient webClient;

    public KarrotBizProfileDetail resolveBizProfileDetails(String bizProfileId){
        String url = "/api/v2/biz_profiles/" + bizProfileId;

        Mono<KarrotBizProfileDetail> response = webClient.mutate()
                .defaultHeader("X-Api-Key", appApiKey)
                .build()
                .get()
                .uri(getRequestUrl(oApiUrl, url))
                .retrieve()
                .onStatus(httpStatus -> httpStatus.is4xxClientError(), (clientResponse) -> {
                    throw new KarrotAuthenticationException(ErrorCode.KARROT_PROFILE_NOT_FOUND);
                })
                .bodyToMono(KarrotBizProfileDetail.class);

        KarrotBizProfileDetail data = response.block();

        if(data.getData().getBizProfile() == null)
            throw new KarrotAuthenticationException(ErrorCode.KARROT_PROFILE_NOT_FOUND);

        return data;
    }

    @Override
    public KarrotUserDetail resolveUserDetails(String karrotAccessToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, "application/json");
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + karrotAccessToken);

        String url = "/api/v1/users/me";

        Mono<KarrotUserDetail> response = webClient.mutate()
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + karrotAccessToken)
                .build()
                .get()
                .uri(getRequestUrl(openApiUrl, url))
                .retrieve()

                .onStatus(httpStatus -> httpStatus.is4xxClientError(), __ -> Mono.error(new KarrotAuthenticationException(ErrorCode.KARROT_BAD_REQUEST)))
                .bodyToMono(KarrotUserDetail.class);


        return response.block();
    }

    @Override
    public <T extends AbstractAccessToken> T resolveAccessToken(String code) {
        return null;
    }

    private String getRequestUrl(String baseUrl, String url){
        StringBuffer sb = new StringBuffer();

        sb.append(baseUrl);
        sb.append(url);

        return sb.toString();
    }

}
