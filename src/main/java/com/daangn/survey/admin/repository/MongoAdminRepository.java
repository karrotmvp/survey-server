package com.daangn.survey.admin.repository;

import com.daangn.survey.admin.dto.AdminResponseDto;
import com.daangn.survey.admin.dto.AdminSurveyDto;
import com.daangn.survey.mongo.aggregate.AggregationResponseSetMongo;
import com.daangn.survey.mongo.survey.SurveyMongo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;


@RequiredArgsConstructor
@Repository
public class MongoAdminRepository {

    private final MongoTemplate mongoTemplate;

    /**
     * Surveys
     */
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

    /**
     * Responses
     */
    public List<AdminResponseDto> getMongoResponses(Long surveyId){
        Criteria criteria = new Criteria().where("surveyId").is(surveyId);

        MatchOperation matchOperation = Aggregation.match(criteria);

        GroupOperation groupOperation = group("responseId").first("surveyId").as("surveyId").first("memberId").as("memberId");

        ProjectionOperation projectionOperation = project( "surveyId", "memberId").and("_id").as("responseId");

        AggregationResults<AdminResponseDto> aggregate = this.mongoTemplate.aggregate(
                newAggregation(matchOperation, groupOperation, projectionOperation),
                "response", AdminResponseDto.class
        );

        return aggregate.getMappedResults();
    }
}
