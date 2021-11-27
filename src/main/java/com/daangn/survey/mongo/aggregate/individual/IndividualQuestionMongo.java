package com.daangn.survey.mongo.aggregate.individual;

import com.daangn.survey.mongo.response.AnswerMongo;
import com.daangn.survey.mongo.survey.QuestionMongo;
import kotlin.internal.AccessibleLateinitPropertyLiteral;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class IndividualQuestionMongo {
    private QuestionMongo question;
    private AnswerMongo answer;
}
