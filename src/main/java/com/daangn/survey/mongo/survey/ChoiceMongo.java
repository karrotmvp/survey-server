package com.daangn.survey.mongo.survey;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter @Setter
public class ChoiceMongo extends QuestionMongo{
    private List<String> choices;

    ChoiceMongo(@NotNull int questionType, Long questionId, String text, String description) {
        super(questionType, questionId, text, description);
    }
}
