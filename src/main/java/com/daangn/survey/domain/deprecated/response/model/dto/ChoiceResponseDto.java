package com.daangn.survey.domain.deprecated.response.model.dto;

import com.daangn.survey.domain.deprecated.response.model.entity.Answerable;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceResponseDto implements Answerable {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String answer;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private int number;
}
