package com.daangn.survey.domain.member.model.mapper;

import com.daangn.survey.domain.member.model.dto.MemberDto;
import com.daangn.survey.domain.member.model.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
    MemberDto toDto(Member member);
}
