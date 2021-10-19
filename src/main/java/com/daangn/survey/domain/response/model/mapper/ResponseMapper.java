package com.daangn.survey.domain.response.model.mapper;

import com.daangn.survey.domain.response.model.dto.SurveyResponseDto;
import com.daangn.survey.domain.response.model.entity.SurveyResponse;
import com.daangn.survey.domain.survey.model.entity.Survey;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ResponseMapper {

    SurveyResponse toEntity(SurveyResponseDto surveyResponseDto);

    default SurveyResponse entityBuilder(SurveyResponseDto surveyResponseDto, Survey survey){
        return SurveyResponse.builder().survey(survey).build();
    }

}