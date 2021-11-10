package com.daangn.survey.domain.aggregation.repository;

import com.daangn.survey.domain.aggregation.model.Aggregatable;
import com.daangn.survey.domain.aggregation.model.ChoiceResponseAggregation;
import com.daangn.survey.domain.aggregation.model.SurveyAggregation;
import com.daangn.survey.domain.question.model.entity.QQuestion;
import com.daangn.survey.domain.response.model.entity.QSurveyResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.daangn.survey.domain.question.model.entity.QChoice.choice;
import static com.daangn.survey.domain.response.model.entity.QChoiceResponse.choiceResponse;
import static com.daangn.survey.domain.response.model.entity.QSurveyResponse.surveyResponse;

@RequiredArgsConstructor
@Repository
public class AggregateRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * select survey_response.survey_id, q.number as "순서", q.text as "질문", group_concat(distinct c.value), count(*)
     * from survey_response
     *     left join choice_response cr on survey_response.survey_response_id = cr.survey_response_id
     *     left join choice c on cr.choice_id = c.choice_id
     *     left join question q on c.question_id = q.question_id
     * where survey_response.survey_id = 30
     * group by c.value
     * order by q.number;
     */

    /**
     *     private Long questionId;
     *     private String question;
     *     private int order;
     *     private Map<String, Integer> choices;
     * @return
     */
    public List<ChoiceResponseAggregation> getChoice(Long surveyId){
        QQuestion question = QQuestion.question;

        return queryFactory.select(Projections.fields(ChoiceResponseAggregation.class,
                        surveyResponse.survey.id.as("surveyId"), // parameter로 대체 가능
                        question.number.as("order"),
                        question.text.as("question"),
                        choice.value.as("value"),
                        choice.value.count().as("count")
                ))
                .from(surveyResponse)
                .leftJoin(choiceResponse).on(surveyResponse.id.eq(choiceResponse.surveyResponse.id))
                .leftJoin(choice).on(choiceResponse.choice.id.eq(choice.id))
                .leftJoin(question).on(choice.question.id.eq(question.id))
                .where(surveyResponse.survey.id.eq(surveyId))
                .groupBy(choice.value)
                .orderBy(question.number.asc())
                .fetch();

    }

    public List<SurveyAggregation> getText(){
        return null;
    }

}
