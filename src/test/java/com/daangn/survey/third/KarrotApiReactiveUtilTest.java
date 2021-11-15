package com.daangn.survey.third;

import com.daangn.survey.core.config.WebClientConfig;
import com.daangn.survey.third.karrot.member.KarrotAccessToken;
import com.daangn.survey.third.karrot.KarrotApiReactiveUtil;
import com.daangn.survey.third.karrot.member.KarrotBizProfileDetail;
import com.daangn.survey.third.karrot.member.KarrotUserDetail;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;


@ActiveProfiles(profiles = "prod")
@SpringBootTest(classes = {KarrotApiReactiveUtil.class, WebClient.class, WebClientConfig.class})
class KarrotApiReactiveUtilTest {

    @Autowired
    private KarrotApiReactiveUtil karrotApiReactiveUtil;

    @Test
    @Disabled
    void resolveBizProfileDetails() {
        String bizProfileId = "57465";
        KarrotBizProfileDetail karrotBizProfileDetail = karrotApiReactiveUtil.resolveBizProfileDetails(bizProfileId);

        System.out.println(karrotBizProfileDetail.getData());
    }

    @Test
    @Disabled
    void resolveUserDetails() {
        String accessToken = "AZDtQChNJe4KFdvn1kJ3HCkn6t4";
        KarrotUserDetail karrotUserDetail = karrotApiReactiveUtil.resolveUserDetails(accessToken);

        System.out.println(karrotUserDetail.getData());
    }

    @Test
    @Disabled
    void resolveAccessToken() {
        String code = "JKbK6aaxGrzqfhUH8yG1";
        KarrotAccessToken karrotAccessToken = karrotApiReactiveUtil.resolveAccessToken(code);

        System.out.println(karrotAccessToken.getAccessToken());

    }
}