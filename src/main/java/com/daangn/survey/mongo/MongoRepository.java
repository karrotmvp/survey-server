package com.daangn.survey.mongo;

import com.daangn.survey.mongo.survey.SurveyMongo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.aggregation.AggregationUpdate.update;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@RequiredArgsConstructor
@Repository
public class MongoRepository {

    private final MongoOperations mongoOps;

    public void insert(Object obj){
        mongoOps.insert(obj);
    }

    public SurveyMongo getOne(int surveyId){

        return mongoOps.findOne(query(where("data.surveyId").is(surveyId)), SurveyMongo.class);
    }

    public void updateResponse(){
        int surveyId = 44;
        mongoOps.update(SurveyMongo.class)
                .matching(query(where("data.surveyId").is(surveyId)))
                .apply((new Update().inc("data.responses.0.questionType", 1)))
                .upsert();

    }
}
