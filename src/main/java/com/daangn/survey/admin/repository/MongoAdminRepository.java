package com.daangn.survey.admin.repository;

import com.daangn.survey.admin.dto.AdminMemberDto;
import com.daangn.survey.admin.dto.AdminResponseDto;
import com.daangn.survey.admin.dto.AdminSurveyCountDto;
import com.daangn.survey.admin.dto.AdminSurveyDto;
import com.daangn.survey.core.log.model.ShortUrlLog;
import com.daangn.survey.mongo.aggregate.AggregationResponseSetMongo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;


@RequiredArgsConstructor
@Repository
public class MongoAdminRepository {

    private final MongoTemplate mongoTemplate;
    /**
     * Members
     */
    public List<AdminMemberDto> getSurveyCounts(List<AdminMemberDto> members){
        Criteria criteria = new Criteria().where("memberId").in(members.stream().map(el -> el.getMemberId()).collect(Collectors.toList()));

        MatchOperation matchOperation = Aggregation.match(criteria);

        GroupOperation groupOperation = group("memberId").count().as("count");

        AggregationResults<AdminSurveyCountDto> aggregate = this.mongoTemplate.aggregate(
                newAggregation(matchOperation, groupOperation),
                "survey", AdminSurveyCountDto.class
        );

        List<AdminSurveyCountDto> list = aggregate.getMappedResults();

        members.stream()
                .forEach(member -> {
                    Optional<AdminSurveyCountDto> optional = list.stream()
                            .filter(el -> el.getId().equals(member.getMemberId()))
                            .findFirst();

                    if(optional.isPresent())
                        member.setSurveyCount(optional.get().getCount());
                });

        return members;
    }

    /**
     * Surveys
     */
    public List<AdminSurveyDto> getSurveys(){
        List<AdminSurveyDto> surveys = mongoTemplate.find(Query.query(where("isDeleted").is(false)), AdminSurveyDto.class, "survey");

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
        Criteria criteria = new Criteria();

        if(surveyId != null) criteria = new Criteria().where("surveyId").is(surveyId);

        MatchOperation matchOperation = Aggregation.match(criteria);

        GroupOperation groupOperation = group("responseId").first("surveyId").as("surveyId").first("memberId").as("memberId").first("createdAt").as("createdAt");

        ProjectionOperation projectionOperation = project( "surveyId", "memberId", "createdAt").and("_id").as("responseId");

        SortOperation sortOperation = sort(Sort.Direction.ASC, "createdAt");

        AggregationResults<AdminResponseDto> aggregate = this.mongoTemplate.aggregate(
                newAggregation(matchOperation, groupOperation, projectionOperation, sortOperation),
                "response", AdminResponseDto.class
        );

        return aggregate.getMappedResults();
    }

    /**
     * user log
     */
    public List<ShortUrlLog> getUserLogs(Long surveyId){
        Query query = new Query(where("surveyId").is(surveyId));

        return mongoTemplate.find(query.with(Sort.by(Sort.Direction.ASC, "createdAt")), ShortUrlLog.class);
    }
}
