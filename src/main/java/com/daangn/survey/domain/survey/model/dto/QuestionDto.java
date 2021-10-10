package com.daangn.survey.domain.survey.model.dto;

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
    private List<ChoiceDto> choiceList;
}
