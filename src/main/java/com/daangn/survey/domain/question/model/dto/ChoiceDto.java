package com.daangn.survey.domain.question.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceDto {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long choiceId;
    private String value;
}
