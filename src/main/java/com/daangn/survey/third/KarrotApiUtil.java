package com.daangn.survey.third;

import com.daangn.survey.core.auth.jwt.model.AccessToken;
import com.daangn.survey.core.auth.oauth.SocialResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class KarrotApiUtil implements SocialResolver {

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

    private final RestTemplate restTemplate;

    public KarrotBizProfileDetail resolveBizProfileDetails(String bizProfileId){
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, "application/json");
        headers.set("X-Api-Key", appApiKey);

        HttpEntity request = new HttpEntity(headers);

        String url = "/api/v2/biz_profiles/" + bizProfileId;

        ResponseEntity<KarrotBizProfileDetail> response = restTemplate.exchange(
                getKarrotOApi(url),
                HttpMethod.GET,
                request,
                KarrotBizProfileDetail.class
        );

        return response.getBody();
    }

    @Override
    public KarrotUserDetail resolveUserDetails(String karrotAccessToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, "application/json");
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + karrotAccessToken);

        HttpEntity request = new HttpEntity(headers);

        String url = "/api/v1/users/me";

        ResponseEntity<KarrotUserDetail> response = restTemplate.exchange(
                getKarrotOpenApi(url),
                HttpMethod.GET,
                request,
                KarrotUserDetail.class
        );

        return response.getBody();
    }

    @Override
    public KarrotAccessToken resolveAccessToken(String code) {

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, "application/json");
        headers.set(HttpHeaders.AUTHORIZATION,"Basic " + Base64.getEncoder().encodeToString((appId + ":" + appSecret).getBytes(StandardCharsets.UTF_8)));

        HttpEntity request = new HttpEntity<>(headers);

        String url = "/oauth/token?code=" + code + "&scope=account/profile&grant_type=authorization_code&response_type=code";

        ResponseEntity<KarrotAccessToken> response = restTemplate.exchange(
                getKarrotOpenApi(url),
                HttpMethod.GET,
                request,
                KarrotAccessToken.class
        );

        return response.getBody();
    }

    private String getKarrotOpenApi(String url){
        StringBuffer sb = new StringBuffer();

        sb.append(openApiUrl);
        sb.append(url);

        return sb.toString();
    }

    private String getKarrotOApi(String url){
        StringBuffer sb = new StringBuffer();

        sb.append(oApiUrl);
        sb.append(url);

        return sb.toString();
    }

}
