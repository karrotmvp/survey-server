package com.daangn.survey.domain.response.model.mapper;

import com.daangn.survey.domain.survey.question.model.mapper.ChoiceMapper;
import com.daangn.survey.domain.response.model.dto.ChoiceResponseDto;
import com.daangn.survey.domain.response.model.dto.SurveyResponseDto;
import com.daangn.survey.domain.response.model.dto.TextResponseDto;
import com.daangn.survey.domain.response.model.entity.ChoiceResponse;
import com.daangn.survey.domain.response.model.entity.SurveyResponse;
import com.daangn.survey.domain.response.model.entity.TextResponse;
import com.daangn.survey.domain.survey.survey.model.entity.Survey;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {ChoiceMapper.class})
public interface ResponseMapper {

    SurveyResponse toEntity(SurveyResponseDto surveyResponseDto);

    default SurveyResponse entityBuilder(SurveyResponseDto surveyResponseDto, Survey survey){
        return SurveyResponse.builder().survey(survey).build();
    }

    TextResponseDto toDtoFromTextResponse(TextResponse textResponse);

    @Mapping(target = "answer", source = "choiceResponse.choice.value")
    @Mapping(target = "number", source = "choiceResponse.choice.number")
    ChoiceResponseDto toDtoFromChoiceResponse(ChoiceResponse choiceResponse);
}
