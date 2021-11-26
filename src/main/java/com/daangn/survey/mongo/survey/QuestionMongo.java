package com.daangn.survey.mongo.survey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class QuestionMongo {

    @NotNull
    private int questionType;

    private Long questionId;

    private String text;

    private String description;

    private List<ChoiceMongo> choices;
}

