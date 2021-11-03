package com.daangn.survey.domain.survey.model.dto;

import com.daangn.survey.domain.member.model.dto.BizProfileDto;
import com.daangn.survey.domain.member.model.entity.BizProfile;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyBriefDto {
    @Schema(description = "설문 ID")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long surveyId;

    private int estimatedTime;

    private int questionCount;

    @Schema(description = "설문 제목", required = true)
    private String title;

    private String description;

    private BizProfileDto bizProfile;

    @Schema(description = "설문 대상", required = true)
    private String target;

    @Schema(description = "설문 생성일", required = true)
    private LocalDateTime createdAt;
}
