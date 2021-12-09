package com.daangn.survey.core.log;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class LogRepository {
    private final MongoTemplate mongoTemplate;

    public void saveUserLog(Object log){
        mongoTemplate.save(log);
    }
}
