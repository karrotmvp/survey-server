package com.daangn.survey.admin.service;

import com.daangn.survey.admin.dto.*;
import com.daangn.survey.admin.repository.MongoAdminRepository;
import com.daangn.survey.admin.repository.QueryRepository;
import com.daangn.survey.mongo.survey.SurveySummaryMongoDto;
import lombok.RequiredArgsConstructor;
import org.dataloader.BatchLoader;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final QueryRepository queryRepository;
    private final MongoAdminRepository mongoAdminRepository;

    /**
     * Members
     */
    @Transactional(readOnly = true)
    public List<AdminMemberDto> getMembersWhere(Integer number, String role){
        return queryRepository.getUsersWhere(number, role);

    }

    /**
     * Surveys
     */
    @Transactional(readOnly = true)
    public List<AdminSurveyDto> getSurveys(){
        List<AdminSurveyDto> surveys = mongoAdminRepository.getSurveys();
        surveys.forEach(el -> el.resolveKorTarget());

        getResponseCount(surveys);
        getMemberInfo(surveys);

        System.out.println();
        return surveys;
    }

    public void getResponseCount(List<AdminSurveyDto> surveys){
        BatchLoader<AdminSurveyDto, AdminSurveyDto> responseCountBatchLoader =
                surveys1 -> CompletableFuture.supplyAsync(() -> mongoAdminRepository.getResponseCounts(surveys1));

        DataLoader<AdminSurveyDto, AdminSurveyDto> responseCountDataLoader = DataLoaderFactory.newDataLoader(responseCountBatchLoader);

        surveys.stream().map(responseCountDataLoader::load).collect(Collectors.toList());

        responseCountDataLoader.dispatchAndJoin();
    }

    public void getMemberInfo(List<AdminSurveyDto> surveys){
        BatchLoader<AdminSurveyDto, AdminSurveyDto> memberBatchLoader =
                surveys1 -> CompletableFuture.supplyAsync(() -> queryRepository.getMemberInfos(surveys1));

        DataLoader<AdminSurveyDto, AdminSurveyDto> memberDataLoader = DataLoaderFactory.newDataLoader(memberBatchLoader);

        surveys.stream().map(memberDataLoader::load).collect(Collectors.toList());

        memberDataLoader.dispatchAndJoin();
    }

    @Transactional(readOnly = true)
    public List<AdminSurveyDto> getSurveysAboutPublished(){
        List<AdminSurveyDto> surveyDtos = queryRepository.getAdminSurveysAboutPublished();
        surveyDtos.forEach(AdminSurveyDto::resolveKorTarget);

        return surveyDtos;
    }

    @Transactional(readOnly = true)
    public List<AdminSurveyDto> getAdminSurveysWhere(Long memberId){
        List<AdminSurveyDto> surveyDtos = queryRepository.getAdminSurveysWhere(memberId);
        surveyDtos.forEach(AdminSurveyDto::resolveKorTarget);

        return surveyDtos;
    }

    /**
     * Responses
     */
    @Transactional(readOnly = true)
    public List<AdminResponseDto> getAdminResponsesWhere(Long surveyId){
        return queryRepository.getAdminResponsesWhere(surveyId);
    }
}
