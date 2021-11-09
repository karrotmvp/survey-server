package com.daangn.survey.admin.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminSurveyDto {
    private Long surveyId;
    private String writer;
    private String title;
    private int responseCount;
    private String target;
    private String publishedAt;
}
