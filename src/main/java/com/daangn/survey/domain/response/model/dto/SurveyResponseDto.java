package com.daangn.survey.domain.response.model.dto;

import com.daangn.survey.domain.question.model.dto.QuestionDto;
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
