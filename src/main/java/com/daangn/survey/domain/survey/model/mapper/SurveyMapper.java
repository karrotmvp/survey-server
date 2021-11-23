package com.daangn.survey.domain.survey.model.mapper;

import com.daangn.survey.domain.member.model.dto.BizProfileDto;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.member.model.mapper.MemberMapper;
import com.daangn.survey.domain.question.model.mapper.QuestionMapper;
import com.daangn.survey.domain.survey.model.dto.SurveyBriefDto;
import com.daangn.survey.domain.survey.model.dto.SurveyDto;
import com.daangn.survey.domain.survey.model.dto.SurveySummaryDto;
import com.daangn.survey.domain.survey.model.entity.Survey;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {QuestionMapper.class})
public interface SurveyMapper {
    @Mapping(target = "surveyId", source = "survey.id")
    @Mapping(target = "responseCount", expression = "java(survey.getSurveyResponses().size())")
    @Mapping(target = "target", expression = "java(survey.convertTarget())")
    SurveySummaryDto toSummaryDto(Survey survey);

    @Mapping(target = "surveyId", source = "survey.id")
    SurveyDto toDetailDto(Survey survey);

    @Mapping(target = "member", source = "member")
    @Mapping(target = "id", ignore = true)
    Survey toEntity(SurveyDto surveyDto, Member member);

    @Mapping(target = "estimatedTime", source = "estimatedTime")
    @Mapping(target = "questionCount", expression = "java(survey.getQuestions().size())")
    @Mapping(target = "target", expression = "java(survey.convertTarget())")
    @Mapping(target = "createdAt", source = "survey.createdAt")
    @Mapping(target = "bizProfile", source = "bizProfileDto")
    SurveyBriefDto toSurveyBriefDtoWithMember(Survey survey, BizProfileDto bizProfileDto, int estimatedTime);
}
