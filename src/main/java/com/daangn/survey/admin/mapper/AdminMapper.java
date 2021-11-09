package com.daangn.survey.admin.mapper;

import com.daangn.survey.admin.dto.AdminResponseDto;
import com.daangn.survey.admin.dto.AdminSurveyDto;
import com.daangn.survey.domain.member.model.mapper.MemberMapper;
import com.daangn.survey.domain.response.model.entity.SurveyResponse;
import com.daangn.survey.domain.survey.model.dto.SurveySummaryDto;
import com.daangn.survey.domain.survey.model.entity.Survey;
import org.hibernate.annotations.Target;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {MemberMapper.class})
public interface AdminMapper {

    @Mapping(target = "responseId", source = "surveyResponse.id")
    @Mapping(target = "member", source = "surveyResponse.member.name")
    AdminResponseDto toAdminResponseDtoFromSurveyResponse(SurveyResponse surveyResponse);

    @Mapping(target = "surveyId", source = "survey.id")
    @Mapping(target = "responseCount", expression = "java(survey.getSurveyResponses().size())")
    @Mapping(target = "target", expression = "java(survey.convertTarget())")
    @Mapping(target = "writer", source = "survey.member.name")
    AdminSurveyDto toAdminSurveyDto(Survey survey);
}
