package com.daangn.survey.mongo;

import com.daangn.survey.mongo.aggregate.AggregationListMongo;
import com.daangn.survey.mongo.aggregate.AggregationMongo;
import com.daangn.survey.mongo.aggregate.AggregationQuestionMongo;
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

    @Transactional(readOnly = true)
    public SurveyMongo getOne(Long surveyId){
        return mongoRepository.getOne(surveyId);
    }

    @Transactional
    public Object insertOne(Object obj){
        return mongoRepository.insert(obj);
    }

    @Transactional(readOnly = true)
    public List<AggregationQuestionMongo> getAggregation(Long surveyId){
        SurveyMongo surveyMongo = mongoRepository.getOne(surveyId);
        List<AggregationListMongo> aggregationListMongos = mongoRepository.getAggregation(surveyId);

        List<AggregationQuestionMongo> result = new LinkedList<>();

        for(int idx = 0 ; idx < aggregationListMongos.size(); idx++){ // question size로 변경할 것
            QuestionMongo questionMongo = surveyMongo.getQuestions().get(idx);

            result.add(new AggregationQuestionMongo(
                    questionMongo.getText(),
                    questionMongo.getQuestionType(),
                    aggregationListMongos.get(idx))
            );
        }

        return result;
    }
}
