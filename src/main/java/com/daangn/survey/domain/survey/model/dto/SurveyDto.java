package com.daangn.survey.domain.survey.model.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyDto {
    private String title;
    private String description;
    private List<QuestionDto> questionList;
}
