package com.daangn.survey.admin.dto;

import com.daangn.survey.domain.question.model.dto.ChoiceDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponseDto{
    private String question;

    private Long questionType;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ChoiceDto> choices;
}
