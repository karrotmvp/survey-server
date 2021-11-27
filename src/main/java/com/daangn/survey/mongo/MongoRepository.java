package com.daangn.survey.mongo;

import com.daangn.survey.mongo.aggregate.AggregationListMongo;
import com.daangn.survey.mongo.aggregate.AggregationMongo;
import com.daangn.survey.mongo.survey.SurveyMongo;
import com.mongodb.BasicDBObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@RequiredArgsConstructor
@Repository
public class MongoRepository {

    private final MongoOperations mongoOps;

    public Object insert(Object obj){
        return mongoOps.insert(obj);
    }

    public SurveyMongo getOne(Long surveyId){

        return mongoOps.findOne(query(where("_id").is(surveyId)), SurveyMongo.class);
    }

    public List<AggregationListMongo> getAggregation(Long surveyId){

        Criteria criteria = new Criteria().where("surveyId").is(surveyId);
        MatchOperation matchOperation = Aggregation.match(criteria);

        UnwindOperation unwindOperation = unwind("answers");
        ProjectionOperation projectionOperation = project("surveyId", "answers").and("_id").as("responseId");

        ConditionalOperators.Cond condOperation = ConditionalOperators.when(Criteria.where("answers.questionType").is(2))
                .then(new BasicDBObject
                        ("answer", "$answers.text").append
                        ("responseId", "$responseId"))
                .otherwise("$$REMOVE");

        GroupOperation groupOperation = group("answers.choice", "answers.order").count().as("count").push(condOperation).as("texts");

        ProjectionOperation projectionOperation1 = project( "count", "texts").and("_id.choice").as("answer").and("_id.order").as("order");
        GroupOperation groupOperation1 = group("order").push("$$ROOT").as("data");


        SortOperation sortOperation = sort(Sort.Direction.ASC, "_id");

        AggregationResults<AggregationListMongo> aggregate = this.mongoOps.aggregate(
                newAggregation(matchOperation,
                                unwindOperation,
                                projectionOperation,
                                groupOperation,
                                projectionOperation1,
                                groupOperation1,
                                sortOperation
                        ),
                "response", AggregationListMongo.class
        );

        return aggregate.getMappedResults();
    }
}
