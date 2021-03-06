package com.daangn.survey.domain.deprecated.survey.survey.model.mapper;

import com.daangn.survey.domain.member.model.dto.BizProfileDto;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.deprecated.survey.question.model.mapper.QuestionMapper;
import com.daangn.survey.domain.deprecated.survey.survey.model.dto.SurveyBriefDto;
import com.daangn.survey.domain.deprecated.survey.survey.model.dto.SurveyDto;
import com.daangn.survey.domain.deprecated.survey.survey.model.dto.SurveyRequestDto;
import com.daangn.survey.domain.deprecated.survey.survey.model.dto.SurveySummaryDto;
import com.daangn.survey.domain.deprecated.survey.survey.model.entity.Survey;
import com.daangn.survey.mongo.survey.SurveyMongo;
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
    @Mapping(target = "createdAt", ignore = true)
    Survey toEntity(SurveyRequestDto surveyRequestDto, Member member);

    @Mapping(target = "estimatedTime", source = "estimatedTime")
    @Mapping(target = "questionCount", expression = "java(survey.getQuestions().size())")
    @Mapping(target = "target", expression = "java(survey.convertTarget())")
    @Mapping(target = "createdAt", source = "survey.createdAt")
    @Mapping(target = "bizProfile", source = "bizProfileDto")
    SurveyBriefDto toSurveyBriefDtoWithMember(Survey survey, BizProfileDto bizProfileDto, int estimatedTime);

    @Mapping(target = "estimatedTime", source = "estimatedTime")
    @Mapping(target = "questionCount", expression = "java(survey.getQuestions().size())")
    @Mapping(target = "target", expression = "java(survey.convertTarget())")
    @Mapping(target = "createdAt", expression = "java(survey.getCreatedAt().minusHours(9L))")
    @Mapping(target = "bizProfile", source = "bizProfileDto")
    SurveyBriefDto toSurveyBriefDtoFromSurveyMongo(SurveyMongo survey, BizProfileDto bizProfileDto, int estimatedTime);
}
