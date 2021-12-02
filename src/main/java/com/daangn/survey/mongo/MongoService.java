package com.daangn.survey.mongo;

import com.daangn.survey.mongo.aggregate.AggregationQuestionMongo;
import com.daangn.survey.mongo.aggregate.individual.IndividualQuestionMongo;
import com.daangn.survey.mongo.response.ResponseMongo;
import com.daangn.survey.mongo.survey.QuestionMongo;
import com.daangn.survey.mongo.survey.SurveyMongo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MongoService {
    private final MongoRepository mongoRepository;

    @Transactional
    public Object insertOne(Object obj){
        return mongoRepository.insert(obj);
    }

    @Transactional
    public void insertSurvey(SurveyMongo survey){
        mongoRepository.insertSurvey(survey);
    }

    @Transactional(readOnly = true)
    public SurveyMongo findSurvey(Long surveyId){
        return mongoRepository.getSurveyMongo(surveyId);
    }

    @Transactional(readOnly = true)
    public List<AggregationQuestionMongo> getAggregation(Long surveyId){
        SurveyMongo surveyMongo = mongoRepository.getSurveyMongo(surveyId);

        List<AggregationQuestionMongo> result = mongoRepository.getAggregation(surveyId);

        for(int idx = 0 ; idx < result.size(); idx++){ // question size로 변경할 것
            QuestionMongo questionMongo = surveyMongo.getQuestions().get(idx);
            result.get(idx).setQuestion(questionMongo.getText());
            result.get(idx).setQuestionType(questionMongo.getQuestionType());
        }

        return result;
    }

    @Transactional(readOnly = true)
    public List<IndividualQuestionMongo> getIndividualResponse(Long responseId){
        List<IndividualQuestionMongo> result = new LinkedList<>();

        ResponseMongo responseMongo = mongoRepository.getResponseMongo(responseId);
        SurveyMongo surveyMongo = mongoRepository.getSurveyMongo(responseMongo.getSurveyId());

        for(int idx = 0 ; idx < responseMongo.getAnswers().size(); idx++){ // 멀티 초이스 고려하기
           result.add(new IndividualQuestionMongo(surveyMongo.getQuestions().get(idx), responseMongo.getAnswers().get(idx)));
        }

        return result;
    }
}
