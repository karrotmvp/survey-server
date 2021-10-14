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

    private final RestTemplate restTemplate;

    @Override
    public KarrotUserDetail resolveUserDetails(String karrotAccessToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, "application/json");
        headers.set(HttpHeaders.AUTHORIZATION,"Bearer " + karrotAccessToken);

        HttpEntity request = new HttpEntity(headers);

        String url = "/api/v1/users/me";

        ResponseEntity<KarrotUserDetail> response = restTemplate.exchange(
                getKarrotApi(url),
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
                getKarrotApi(url),
                HttpMethod.GET,
                request,
                KarrotAccessToken.class
        );

        return response.getBody();
    }

    public String getKarrotApi(String url){
        StringBuffer sb = new StringBuffer();

        sb.append(openApiUrl);
        sb.append(url);

        return sb.toString();
    }
}
