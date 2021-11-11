package com.daangn.survey.domain.aggregation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAggregation {
    private Long questionId;
    private int order;
    private String question;
    private Long questionType;
    private List<Aggregatable> answers;

    public void setChoiceAnswers(List<ChoiceResponseAggregation> answers) {
        List<Aggregatable> list = new LinkedList<>();
        list.addAll(answers);
        this.answers = list;
    }

    public void setTextAnswers(List<TextResponseAggregation> answers) {
        List<Aggregatable> list = new LinkedList<>();
        list.addAll(answers);
        this.answers = list;
    }
}

