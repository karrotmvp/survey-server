package com.daangn.survey.domain.survey.model.dto;

import com.daangn.survey.domain.question.model.dto.QuestionDto;
import com.daangn.survey.domain.survey.model.entity.Target;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;
import java.util.Locale;

@Builder
@Getter @Setter
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

    @Schema(description = "설문에 대한 설명",
            example = "example description", required = true)
    private String description;

    @Schema(description = "설문 대상",
            example = "1", required = true, allowableValues = {"0 : 모든 이웃", "1 : 단골 이웃", "2 : 비즈 프로필을 방문한 이웃"})
    private int target;

    @Schema(description = "질문들", required = true)
    private List<QuestionDto> questions;
}
