package com.daangn.survey.domain.member.model.mapper;

import com.daangn.survey.admin.dto.AdminMemberDto;
import com.daangn.survey.domain.member.model.dto.MemberDto;
import com.daangn.survey.domain.member.model.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
    MemberDto toDto(Member member);

    @Mapping(target = "memberId", source = "id")
    @Mapping(target = "surveyCount", expression = "java(member.getSurveys().size())")
    AdminMemberDto toAdminMemberDto(Member member);
}
