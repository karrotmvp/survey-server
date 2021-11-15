package com.daangn.survey.third.karrot;

import com.daangn.survey.core.auth.oauth.SocialResolver;
import com.daangn.survey.core.error.ErrorCode;
import com.daangn.survey.core.error.exception.KarrotAuthenticationException;
import com.daangn.survey.third.karrot.member.KarrotAccessToken;
import com.daangn.survey.third.karrot.member.KarrotBizProfileDetail;
import com.daangn.survey.third.karrot.member.KarrotUserDetail;
import com.daangn.survey.third.karrot.scheme.KarrotSchemeUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
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
                getRequestUrl(oApiUrl, url),
                HttpMethod.GET,
                request,
                KarrotBizProfileDetail.class
        );

        if(response.getBody().getData().getBizProfile() == null)
            throw new KarrotAuthenticationException(ErrorCode.KARROT_PROFILE_NOT_FOUND);

        return response.getBody();
    }

    @Override
    public KarrotUserDetail resolveUserDetails(String karrotAccessToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, "application/json");
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + karrotAccessToken);

        HttpEntity request = new HttpEntity(headers);

        String url = "/api/v1/users/me";

        try {
            ResponseEntity<KarrotUserDetail> response = restTemplate.exchange(
                    getRequestUrl(openApiUrl, url),
                    HttpMethod.GET,
                    request,
                    KarrotUserDetail.class
            );

            return response.getBody();

        } catch (HttpClientErrorException e){
            throw new KarrotAuthenticationException(e.getResponseBodyAsString(), ErrorCode.KARROT_BAD_REQUEST);
        }
    }

    @Override
    public KarrotAccessToken resolveAccessToken(String code) {

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, "application/json");
        headers.set(HttpHeaders.AUTHORIZATION,"Basic " + Base64.getEncoder().encodeToString((appId + ":" + appSecret).getBytes(StandardCharsets.UTF_8)));

        HttpEntity request = new HttpEntity<>(headers);

        String url = "/oauth/token?code=" + code + "&scope=account/profile&grant_type=authorization_code&response_type=code";

        try {
            ResponseEntity<KarrotAccessToken> response = restTemplate.exchange(
                    getRequestUrl(openApiUrl, url),
                    HttpMethod.GET,
                    request,
                    KarrotAccessToken.class
            );

            return response.getBody();

        } catch (HttpClientErrorException e){

            throw new KarrotAuthenticationException(e.getResponseBodyAsString(), ErrorCode.KARROT_BAD_REQUEST);
        }
    }

    private String getRequestUrl(String baseUrl, String url){
        StringBuffer sb = new StringBuffer();

        sb.append(baseUrl);
        sb.append(url);

        return sb.toString();
    }

    public KarrotSchemeUrl resolveSchemeUrl(String toUrl){
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, "application/json");
        headers.set("X-Api-Key", appApiKey);

        HttpEntity request = new HttpEntity(headers);

        String url = "/api/v2/widget/entry_target_uri?to=" + toUrl;

        ResponseEntity<KarrotSchemeUrl> response = restTemplate.exchange(
                getRequestUrl(oApiUrl, url),
                HttpMethod.GET,
                request,
                KarrotSchemeUrl.class
        );

        if(response.getBody().getData() == null)
            throw new KarrotAuthenticationException(ErrorCode.KARROT_SCHEME_RESOLVE_FAILURE);

        return response.getBody();
    }

}
