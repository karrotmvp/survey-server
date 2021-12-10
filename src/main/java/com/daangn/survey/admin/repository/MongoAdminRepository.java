package com.daangn.survey.admin.repository;

import com.daangn.survey.admin.dto.AdminSurveyDto;
import com.daangn.survey.mongo.aggregate.AggregationResponseSetMongo;
import com.daangn.survey.mongo.survey.SurveyMongo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;


@RequiredArgsConstructor
@Repository
public class MongoAdminRepository {

    private final MongoTemplate mongoTemplate;

    public List<AdminSurveyDto> getSurveys(){
        List<AdminSurveyDto> surveys = mongoTemplate.findAll(AdminSurveyDto.class, "survey");

        return surveys;
    }

    public List<AdminSurveyDto> getResponseCounts(List<AdminSurveyDto> surveys){
        Criteria criteria = new Criteria().where("surveyId").in(surveys.stream().map(el -> el.getId()).collect(Collectors.toList()));

        MatchOperation matchOperation = Aggregation.match(criteria);

        GroupOperation firstGroupOperation = group("responseId").count().as("count").first("surveyId").as("surveyId");

        GroupOperation secondGroupOperation = group("surveyId").addToSet("_id").as("set");

        AggregationResults<AggregationResponseSetMongo> aggregate = this.mongoTemplate.aggregate(
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
}
