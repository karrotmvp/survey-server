package com.daangn.survey.domain.deprecated.response.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyResponseDto {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long surveyId;
    private List<CommonResponseDto> responses;
}
