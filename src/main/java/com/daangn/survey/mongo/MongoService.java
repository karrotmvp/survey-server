package com.daangn.survey.mongo;

import com.daangn.survey.mongo.survey.SurveyMongo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MongoService {
    private final MongoRepository mongoRepository;

    @Transactional(readOnly = true)
    public SurveyMongo getOne(int surveyId){
        return mongoRepository.getOne(surveyId);
    }

    @Transactional
    public void insertOne(SurveyMongo surveyMongo){
        mongoRepository.insert(surveyMongo);
    }

    @Transactional
    public void saveSurveyResponseDto(){
        mongoRepository.updateResponse();
    }
}
