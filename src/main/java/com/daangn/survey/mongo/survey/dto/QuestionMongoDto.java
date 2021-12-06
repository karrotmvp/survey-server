package com.daangn.survey.mongo.survey.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class QuestionMongoDto {
    @JsonProperty("questionId")
    private Long id;

    private int questionType;

    private String text;

    private String description;

    private List<ChoiceMongoDto> choices;
}
