package com.daangn.survey.third;

import com.daangn.survey.third.karrot.member.KarrotAccessToken;
import com.daangn.survey.third.karrot.KarrotApiUtil;
import com.daangn.survey.third.karrot.member.KarrotBizProfileDetail;
import com.daangn.survey.third.karrot.member.KarrotUserDetail;
import com.daangn.survey.third.karrot.scheme.KarrotSchemeUrl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@ActiveProfiles(profiles = "prod")
@SpringBootTest(classes = {KarrotApiUtil.class, RestTemplate.class})
class KarrotApiUtilTest {

    @Autowired
    private KarrotApiUtil karrotApiUtil;

    @Value("${mudda.front-url}")
    private String frontUrl;

    @Test
    @Disabled
    void resolveBizProfileDetails() {
        String bizProfileId = "1272507";
        KarrotBizProfileDetail karrotBizProfileDetail = karrotApiUtil.resolveBizProfileDetails(bizProfileId);

        System.out.println(karrotBizProfileDetail.getData());
    }

    @Test
    @Disabled
    void resolveUserDetails() {
        String accessToken = "AZDtQChNJe4KFdvn1kJ3HCkn6t4";
        KarrotUserDetail karrotUserDetail = karrotApiUtil.resolveUserDetails(accessToken);

        System.out.println(karrotUserDetail.getData());
    }

    @Test
    @Disabled
    void resolveAccessToken() {
        String code = "JKbK6aaxGrzqfhUH8yG1";
        KarrotAccessToken karrotAccessToken = karrotApiUtil.resolveAccessToken(code);

        System.out.println(karrotAccessToken.getAccessToken());

    }

    @Test
    void resolveSchemeUrl(){
        KarrotSchemeUrl schemeUrl = karrotApiUtil.resolveSchemeUrl(frontUrl + "/survey/23");

        System.out.println(schemeUrl.getData());

    }

}