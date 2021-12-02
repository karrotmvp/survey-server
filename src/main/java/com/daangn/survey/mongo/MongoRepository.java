package com.daangn.survey.mongo;

import com.daangn.survey.mongo.aggregate.AggregationQuestionMongo;
import com.daangn.survey.mongo.response.ResponseMongo;
import com.daangn.survey.mongo.response.ResponseMongoDto;
import com.daangn.survey.mongo.survey.SurveyMongo;
import com.mongodb.BasicDBObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@RequiredArgsConstructor
@Repository
public class MongoRepository {

    private final MongoOperations mongoOps;

    public void insertResponses(List<ResponseMongo> responses){
        mongoOps.insertAll(responses);
    }

    public void insertSurvey(SurveyMongo surveyMongo){
        mongoOps.insertAll(surveyMongo.getQuestions());
        mongoOps.insert(surveyMongo);
    }

    public SurveyMongo getSurveyMongo(Long surveyId){

        return mongoOps.findOne(query(where("_id").is(surveyId)), SurveyMongo.class);
    }

    public ResponseMongoDto getResponseMongo(Long responseId){
        return mongoOps.findOne(query(where("_id").is(responseId)), ResponseMongoDto.class);
    }

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
