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
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long surveyId;
    private String title;
    private int responseCount;
    private LocalDateTime createdAt;
}
