package com.daangn.survey.mongo.aggregate.individual;

import com.daangn.survey.mongo.response.AnswerMongoDto;
import com.daangn.survey.mongo.survey.QuestionMongo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class IndividualQuestionMongo {
    private QuestionMongo question;
    private AnswerMongoDto answer;
}
