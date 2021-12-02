package com.daangn.survey.domain.member.model.mapper;

import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.third.karrot.member.KarrotBizProfileDetail;
import com.daangn.survey.third.karrot.member.KarrotUserDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberMapperTest {

    @Autowired
    private MemberMapper memberMapper;

    @Test
    void toMemberEntityFromBiz() {
        KarrotBizProfileDetail.KarrotBizProfileData.KarrotBizProfile.Region region = new KarrotBizProfileDetail.KarrotBizProfileData.KarrotBizProfile.Region("id", "name", "region1Id", "region1Name", "region2Id", "region2Name", "region3Id", "region3Name");
        KarrotBizProfileDetail.KarrotBizProfileData.KarrotBizProfile.Category category = new KarrotBizProfileDetail.KarrotBizProfileData.KarrotBizProfile.Category("id", "name");
        KarrotBizProfileDetail.KarrotBizProfileData.KarrotBizProfile profile = new KarrotBizProfileDetail.KarrotBizProfileData.KarrotBizProfile("id", "name", "imageUrl", region, "profileUrl", category, 0, Arrays.asList());
        KarrotBizProfileDetail.KarrotBizProfileData data = new KarrotBizProfileDetail.KarrotBizProfileData(profile);
        KarrotBizProfileDetail detail = new KarrotBizProfileDetail(data);

        Member member = memberMapper.toMemberEntityFromBiz(detail, "ROLE_BIZ");

        assertEquals(member.getDaangnId(), profile.getId());
        assertEquals(member.getName(), profile.getName());
        assertEquals(member.getImageUrl(), profile.getImageUrl());
        assertEquals(member.getProfileUrl(), profile.getProfileUrl());
        assertEquals(member.getRegion(), detail.parseRegion());

    }

    @Test
    void toMemberEntityFromUser() {
        KarrotUserDetail.KarrotUserData data = new KarrotUserDetail.KarrotUserData("nickname", "userId");
        KarrotUserDetail detail = new KarrotUserDetail();
        detail.setData(data);

        Member member = memberMapper.toMemberEntityFromUser(detail, "ROLE_USER");

        assertEquals(member.getDaangnId(), data.getUserId());
        assertEquals(member.getName(), data.getNickname());
    }

    @Test
    void updateMember() {
        Member oldMember = Member.builder().daangnId("oldDaangnId").name("oldName").imageUrl("oldImageUrl").build();

        Member newMember = Member.builder().daangnId("newDaangnId").name("newName").region("newRegion").build();

        memberMapper.updateMember(oldMember, newMember);

        assertEquals(oldMember.getName(), newMember.getName());
        assertEquals(oldMember.getDaangnId(), newMember.getDaangnId());
        assertEquals(oldMember.getImageUrl(), "oldImageUrl");
        assertEquals(oldMember.getRegion(), newMember.getRegion());
    }
}