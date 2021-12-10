package com.daangn.survey.domain.deprecated.aggregation.repository;

import com.daangn.survey.domain.deprecated.aggregation.model.ChoiceResponseAggregation;
import com.daangn.survey.domain.deprecated.aggregation.model.QuestionAggregation;
import com.daangn.survey.domain.deprecated.aggregation.model.TextResponseAggregation;
import com.daangn.survey.domain.deprecated.survey.question.model.entity.QQuestion;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.daangn.survey.domain.deprecated.survey.question.model.entity.QChoice.choice;
import static com.daangn.survey.domain.deprecated.survey.question.model.entity.QQuestion.question;
import static com.daangn.survey.domain.deprecated.response.model.entity.QChoiceResponse.choiceResponse;
import static com.daangn.survey.domain.deprecated.response.model.entity.QSurveyResponse.surveyResponse;
import static com.daangn.survey.domain.deprecated.response.model.entity.QTextResponse.textResponse;

@RequiredArgsConstructor
@Repository
public class AggregateRepository {
    private final JPAQueryFactory queryFactory;

    public List<QuestionAggregation> getQuestions(Long surveyId){
        return queryFactory.select(Projections.fields(QuestionAggregation.class,
                    question.id.as("questionId"),
                    question.number.as("order"),
                    question.questionType.id.as("questionType"),
                    question.text.as("question")
                ))
                .from(question)
                .where(question.survey.id.eq(surveyId))
                .fetch();
    }

    public List<ChoiceResponseAggregation> getChoiceAggregation(Long questionId){
        QQuestion question = QQuestion.question;

        return queryFactory.select(Projections.fields(ChoiceResponseAggregation.class,
                        choice.value.as("value"),
                        choice.value.count().as("count")
                ))
                .from(question)
                .leftJoin(choiceResponse).on(question.id.eq(choiceResponse.question.id))
                .leftJoin(choice).on(choiceResponse.choice.id.eq(choice.id))
                .where(question.id.eq(questionId))
                .groupBy(choice.value)
                .fetch();

    }

    public List<TextResponseAggregation> getTextAggregation(Long questionId){
        QQuestion question = QQuestion.question;

        return queryFactory.select(Projections.fields(TextResponseAggregation.class,
                        textResponse.surveyResponse.id.as("surveyResponseId"),
                        textResponse.answer.as("answer")
                ))
                .from(question)
                .leftJoin(textResponse).on(textResponse.question.id.eq(question.id))
                .where(question.id.eq(questionId))
                .orderBy(textResponse.createdAt.asc())
                .fetch();
    }

    public List<Long> getSurveyResponseIds(Long survyeyId){
        return queryFactory.select(
                    surveyResponse.id
                ).from(surveyResponse)
                .where(surveyResponse.survey.id.eq(survyeyId))
                .fetch();
    }

}
