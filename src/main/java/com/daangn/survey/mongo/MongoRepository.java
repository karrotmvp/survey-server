package com.daangn.survey.mongo;

import com.daangn.survey.mongo.aggregate.AggregationAnswerMongo;
import com.daangn.survey.mongo.aggregate.AggregationQuestionMongo;
import com.daangn.survey.mongo.aggregate.AggregationResponseSetMongo;
import com.daangn.survey.mongo.aggregate.SurveyResponseCountMongo;
import com.daangn.survey.mongo.aggregate.individual.IndividualResponseMongo;
import com.daangn.survey.mongo.response.ResponseMongo;
import com.daangn.survey.mongo.survey.SurveyMongo;
import com.daangn.survey.mongo.survey.SurveySummaryMongoDto;
import com.daangn.survey.mongo.survey.dto.SurveyMongoDto;
import com.mongodb.BasicDBObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@RequiredArgsConstructor
@Repository
public class MongoRepository {

    private final MongoOperations mongoOps;

    // Survey
    public Long insertSurvey(SurveyMongo surveyMongo){
        mongoOps.insertAll(surveyMongo.getQuestions());
        mongoOps.insert(surveyMongo);

        return surveyMongo.getId();
    }

    public SurveyMongoDto getSurveyMongoDto(Long surveyId){

        return mongoOps.findOne(query(where("_id").is(surveyId).and("isDeleted").is(false)), SurveyMongoDto.class, "survey");
    }

    public SurveyMongo findSurveyMongo(Object surveyId){

        return mongoOps.findOne(query(where("_id").is(surveyId).and("isDeleted").is(false)), SurveyMongo.class);
    }

    public List<SurveySummaryMongoDto> findSurveysByMemberId(Long memberId){
        List<SurveySummaryMongoDto> surveySummaries = mongoOps.find(query(where("memberId").is(memberId).and("isDeleted").is(false)), SurveySummaryMongoDto.class, "survey");

        return surveySummaries;
    }

    public void deleteSurvey(Long surveyId){

        Update update = new Update();
        update.set("isDeleted", true);

        mongoOps.findAndModify(query(where("_id").is(surveyId)), update, SurveyMongo.class);
    }

    // Response
    public void insertResponses(List<ResponseMongo> responses){
        mongoOps.insertAll(responses);
    }

    public List<IndividualResponseMongo> getResponse(Long questionId, Long responseId){
        Criteria criteria = new Criteria().where("questionId").is(questionId).and("responseId").is(responseId);
        MatchOperation matchOperation = Aggregation.match(criteria);

        ProjectionOperation projectionOperation = project( "questionType", "value");

        AggregationResults<IndividualResponseMongo> aggregate = this.mongoOps.aggregate(
                newAggregation(matchOperation, projectionOperation),
                "response", IndividualResponseMongo.class
        );

        return aggregate.getMappedResults();
    }

    // Aggregation
    public List<AggregationAnswerMongo.TextAnswerMongo> getTextAnswers(Long surveyId, Long questionId){
        Criteria criteria = new Criteria().where("questionId").is(questionId).and("surveyId").is(surveyId);
        MatchOperation matchOperation = Aggregation.match(criteria);

        ProjectionOperation projectionOperation = project( "value").and("_id").as("responseId");

        AggregationResults<AggregationAnswerMongo.TextAnswerMongo> aggregate = this.mongoOps.aggregate(
                newAggregation(matchOperation, projectionOperation),
                "response", AggregationAnswerMongo.TextAnswerMongo.class
        );

        return aggregate.getMappedResults();
    }

    public List<AggregationAnswerMongo.ChoiceAnswerMongo> getChoiceAnswers(Long surveyId, Long questionId){
        Criteria criteria = new Criteria().where("questionId").is(questionId).and("surveyId").is(surveyId);
        MatchOperation matchOperation = Aggregation.match(criteria);

        GroupOperation groupOperation = group("value")
                .count().as("count");

        ProjectionOperation projectionOperation = project( "count").and("_id").as("value");

        AggregationResults<AggregationAnswerMongo.ChoiceAnswerMongo> aggregate = this.mongoOps.aggregate(
                newAggregation(matchOperation, groupOperation, projectionOperation),
                "response", AggregationAnswerMongo.ChoiceAnswerMongo.class
        );

        return aggregate.getMappedResults();
    }

    public List<Long> getResponseBrief(Long surveyId){
        return mongoOps.findDistinct(query(where("surveyId").is(surveyId)),"responseId", ResponseMongo.class, Long.class);
    }

    public List<SurveySummaryMongoDto> getResponses(List<SurveySummaryMongoDto> surveys){

        Criteria criteria = new Criteria().where("surveyId").in(surveys.stream().map(el -> el.getId()).collect(Collectors.toList()));

        MatchOperation matchOperation = Aggregation.match(criteria);

        GroupOperation firstGroupOperation = group("responseId").count().as("count").first("surveyId").as("surveyId");

        GroupOperation secondGroupOperation = group("surveyId").addToSet("_id").as("set");

        AggregationResults<AggregationResponseSetMongo> aggregate = this.mongoOps.aggregate(
                newAggregation(matchOperation, firstGroupOperation, secondGroupOperation),
                "response", AggregationResponseSetMongo.class
        );

        List<AggregationResponseSetMongo> list = aggregate.getMappedResults();

        surveys.stream()
                .forEach(survey -> {
                    Optional<AggregationResponseSetMongo> optional = list.stream()
                                                                        .filter(el -> el.getId().equals(survey.getId()))
                                                                        .findFirst();

                    if(optional.isPresent())
                        survey.setResponseCount(optional.get().getSet().size());
                });

        return surveys;
    }

    /**
     * Deprecated
     */
    public List<SurveyResponseCountMongo> getSurveyResponseCountList(List<Long> surveyIds){
        Criteria criteria = new Criteria().where("surveyId").in(surveyIds);
        MatchOperation matchOperation = Aggregation.match(criteria);

        GroupOperation groupOperation = group("surveyId").addToSet("responseId").as("responseSet");

        ProjectionOperation projectionOperation = project().and("_id").as("surveyId").andExclude("_id").and("responseSet").size().as("count");;

        SortOperation sortOperation = sort(Sort.Direction.ASC, "surveyId");

        AggregationResults<SurveyResponseCountMongo> aggregate = this.mongoOps.aggregate(
                newAggregation(matchOperation, groupOperation, projectionOperation, sortOperation),
                "response", SurveyResponseCountMongo.class
        );

        return aggregate.getMappedResults();
    }

    /**
     * Deprecated
     */
    public List<AggregationQuestionMongo> getAggregation(Long surveyId){

        Criteria criteria = new Criteria().where("surveyId").is(surveyId);
        MatchOperation matchOperation = Aggregation.match(criteria);

        UnwindOperation unwindOperation = unwind("answers");

        ProjectionOperation projectionOperation = project("surveyId", "answers").and("_id").as("responseId");

        ConditionalOperators.Cond condOperation = ConditionalOperators
                                                    .when(Criteria.where("answers.questionType").is(2))
                                                    .then(new BasicDBObject
                                                            ("answer", "$answers.text").append
                                                            ("responseId", "$responseId"))
                                                    .otherwise("$$REMOVE");

        GroupOperation groupOperation = group("answers.choice", "answers.order")
                                        .count().as("count")
                                        .push(condOperation).as("texts");

        ProjectionOperation projectionOperation1 = project( "count", "texts").and("_id.choice").as("answer").and("_id.order").as("order");

        GroupOperation groupOperation1 = group("order")
                .push("$$ROOT").as("answers");


        SortOperation sortOperation = sort(Sort.Direction.ASC, "_id");

        AggregationResults<AggregationQuestionMongo> aggregate = this.mongoOps.aggregate(
                newAggregation(matchOperation,
                                unwindOperation,
                                projectionOperation,
                                groupOperation,
                                projectionOperation1,
                                groupOperation1,
                                sortOperation
                        ),
                "response", AggregationQuestionMongo.class
        );

        return aggregate.getMappedResults();
    }
}
