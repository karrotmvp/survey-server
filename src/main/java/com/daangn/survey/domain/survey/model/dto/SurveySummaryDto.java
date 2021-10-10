package com.daangn.survey.domain.survey.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveySummaryDto {
    private String title;
    private int responseCount;
    private LocalDateTime createdAt;
}
