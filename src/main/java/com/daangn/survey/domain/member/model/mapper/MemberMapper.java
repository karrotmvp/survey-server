package com.daangn.survey.domain.member.model.mapper;

import com.daangn.survey.admin.dto.AdminMemberDto;
import com.daangn.survey.domain.member.model.dto.MemberDto;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.third.KarrotBizProfileDetail;
import com.daangn.survey.third.KarrotUserDetail;
import org.mapstruct.*;

import javax.persistence.Column;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MemberMapper {
    MemberDto toDto(Member member);

    Member toEntity(MemberDto memberDto);

    @Mapping(target = "memberId", source = "id")
    @Mapping(target = "surveyCount", expression = "java(member.getSurveys().size())")
    AdminMemberDto toAdminMemberDto(Member member);

    @Mapping(target = "daangnId", source = "profile.data.bizProfile.id")
    @Mapping(target = "name", source = "profile.data.bizProfile.name")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "imageUrl", source = "profile.data.bizProfile.imageUrl")
    @Mapping(target = "region", expression = "java(profile.parseRegion())")
    @Mapping(target = "profileUrl", source = "profile.data.bizProfile.profileUrl")
    Member toMemberEntityFromBiz(KarrotBizProfileDetail profile, String role);

    void updateMember(@MappingTarget Member member, Member newMember);

    @Mapping(target = "daangnId", source = "profile.data.userId")
    @Mapping(target = "name", source = "profile.data.nickname")
    @Mapping(target = "role", source = "role")
    Member toMemberEntityFromUser(KarrotUserDetail profile, String role);

}
