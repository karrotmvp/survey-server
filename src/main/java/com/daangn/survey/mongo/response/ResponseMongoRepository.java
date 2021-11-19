package com.daangn.survey.mongo.response;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@RequiredArgsConstructor
@Repository
public class ResponseMongoRepository {

    private final MongoOperations mongoOps;

    public void insert(Object obj){
        mongoOps.insert(obj);
    }

    public TestEntity getOne(int surveyId){

        return mongoOps.findOne(query(where("data.surveyId").is(surveyId)), TestEntity.class);
    }
}
