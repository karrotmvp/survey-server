package com.daangn.survey.domain.deprecated.survey.survey.model.dto;


import com.daangn.survey.domain.deprecated.survey.question.model.dto.QuestionDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyDto {
    @Schema(description = "설문 ID",
            example = "1", readOnly = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long surveyId;

    @Schema(description = "설문 제목",
            example = "example title", required = true)
    private String title;

    @Schema(description = "설문 대상",
            example = "1", required = true, allowableValues = {"1", "2", "3"})
    private int target;

    @Schema(description = "질문들", required = true)
    private List<QuestionDto> questions;

    @Schema(description = "생성일")
    private LocalDateTime createdAt;
}
