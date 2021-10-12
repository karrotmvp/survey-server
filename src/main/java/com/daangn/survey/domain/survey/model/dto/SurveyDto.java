package com.daangn.survey.domain.survey.model.dto;

import com.daangn.survey.domain.question.model.dto.QuestionDto;
import com.daangn.survey.domain.survey.model.entity.Target;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;
import java.util.Locale;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyDto {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long surveyId;
    private String title;
    private String description;
    private int target;
    private List<QuestionDto> questions;
}
