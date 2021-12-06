package com.daangn.survey.mongo.survey.dto;

import com.daangn.survey.mongo.survey.QuestionMongo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SurveyMongoDto {

    private String title;

    private int target;

    private List<QuestionMongoDto> questions;

    private LocalDateTime createdAt;
}
