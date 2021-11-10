package com.daangn.survey.admin.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponseDto {
    private Long responseId;
    private String surveyTitle;
    private String member;
    private LocalDateTime createdAt;
}
