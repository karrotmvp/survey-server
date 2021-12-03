package com.daangn.survey.mongo.aggregate.individual;

import com.daangn.survey.mongo.survey.QuestionMongo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class IndividualQuestionMongo {
    private QuestionMongo question;
    private List<IndividualResponseMongo> answer;
}
