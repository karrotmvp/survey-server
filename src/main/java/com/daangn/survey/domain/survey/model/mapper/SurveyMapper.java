package com.daangn.survey.domain.survey.model.mapper;

import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.survey.model.dto.SurveyDto;
import com.daangn.survey.domain.survey.model.dto.SurveySummaryDto;
import com.daangn.survey.domain.survey.model.entity.Survey;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SurveyMapper {
    @Mapping(target = "responseCount", expression = "java(survey.getSurveyResponses().size())")
    SurveySummaryDto toSummaryDto(Survey survey);

    default Survey entityBuilder(SurveyDto surveyDto, Member member){
        return Survey.builder()
                .member(member)
                .title(surveyDto.getTitle())
                .description(surveyDto.getDescription())
                .build();
    }
}
