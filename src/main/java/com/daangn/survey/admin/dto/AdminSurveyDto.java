package com.daangn.survey.admin.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminSurveyDto {
    private long surveyId;
    private String writer;
    private String title;
    private int responseCount;
    private int target;
    private LocalDateTime publishedAt;
}
