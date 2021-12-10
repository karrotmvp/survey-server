package com.daangn.survey.admin.repository;

import com.daangn.survey.admin.dto.AdminSurveyDto;
import com.daangn.survey.mongo.survey.SurveyMongo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@RequiredArgsConstructor
@Repository
public class MongoAdminRepository {

    private final MongoTemplate mongoTemplate;

    public List<AdminSurveyDto> getSurveys(){
        List<AdminSurveyDto> surveys = mongoTemplate.findAll(AdminSurveyDto.class, "survey");

        return surveys;
    }
}
