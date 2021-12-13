package com.daangn.survey.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminFeedbackDto {
    private String name;
    private String question;
    private String answer;
    private LocalDateTime createdAt;
}
