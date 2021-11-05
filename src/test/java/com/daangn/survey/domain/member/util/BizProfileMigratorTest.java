package com.daangn.survey.domain.member.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BizProfileMigratorTest {

    @Autowired
    private BizProfileMigrator bizProfileMigrator;

    @Test
    void getMembersDaangnId() {
        List<String> daangnIdList = bizProfileMigrator.getMembersDaangnId();
        System.out.println(daangnIdList);
    }
}