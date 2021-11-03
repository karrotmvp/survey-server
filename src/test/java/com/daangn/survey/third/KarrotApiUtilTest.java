package com.daangn.survey.third;

import com.daangn.survey.third.karrot.KarrotAccessToken;
import com.daangn.survey.third.karrot.KarrotApiUtil;
import com.daangn.survey.third.karrot.KarrotBizProfileDetail;
import com.daangn.survey.third.karrot.KarrotUserDetail;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@ActiveProfiles(profiles = "prod")
@SpringBootTest(classes = {KarrotApiUtil.class, RestTemplate.class})
class KarrotApiUtilTest {

    @Autowired
    private KarrotApiUtil karrotApiUtil;

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

}