package com.daangn.survey.domain.survey.model.mapper;

import com.daangn.survey.domain.survey.model.dto.SurveyDto;
import com.daangn.survey.domain.survey.model.entity.Survey;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SurveyMapper {

    default Survey entityBuilder(SurveyDto surveyDto){
        return Survey.builder()
                .title(surveyDto.getTitle())
                .description(surveyDto.getDescription())
                .build();
    }
}
