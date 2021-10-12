package com.daangn.survey.domain.question.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
    private Long questionType;
    private String text;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ChoiceDto> choices;
}
