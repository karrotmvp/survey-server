package com.daangn.survey.mongo.survey;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveySummaryMongoDto {
    @Schema(description = "설문 ID")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("surveyId")
    private Long id;

    @Schema(description = "설문 제목", required = true)
    private String title;

    @Schema(description = "설문 응답 개수", required = true)
    private int responseCount;

    @Schema(description = "설문 대상", required = true)
    private int target;

    @Schema(description = "설문 생성일", required = true)
    private LocalDateTime createdAt;

    public SurveySummaryMongoDto setResponseCount(int count){
        this.responseCount = count;
        return this;
    }

    public SurveySummaryMongoDto calculateCreatedAt(){
        this.createdAt = createdAt.minusHours(9L);
        return this;
    }
}

