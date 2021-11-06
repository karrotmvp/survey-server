package com.daangn.survey.domain.response.model.dto;

import com.daangn.survey.domain.response.model.entity.Answerable;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TextResponseDto implements Answerable {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String answer;
}
