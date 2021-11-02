package com.daangn.survey.domain.survey.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveySummaryDto {
    @Schema(description = "설문 ID")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long surveyId;

    @Schema(description = "설문 제목", required = true)
    private String title;

    @Schema(description = "설문 응답 개수", required = true)
    private int responseCount;

    @Schema(description = "설문 대상", required = true)
    private String target;

    @Schema(description = "설문 생성일", required = true)
    private LocalDateTime createdAt;
}
