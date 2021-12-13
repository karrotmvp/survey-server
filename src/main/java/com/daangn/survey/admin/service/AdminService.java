package com.daangn.survey.admin.service;

import com.daangn.survey.admin.dto.*;
import com.daangn.survey.admin.repository.MongoAdminRepository;
import com.daangn.survey.admin.repository.QueryRepository;
import com.daangn.survey.common.util.shorturl.model.entity.ShortUrl;
import com.daangn.survey.common.util.shorturl.repository.UrlRepository;
import com.daangn.survey.core.log.model.ShortUrlLog;
import com.daangn.survey.mongo.survey.SurveySummaryMongoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final UrlRepository urlRepository;

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
    public List<AdminSurveyDto> getMongoSurveys(){
        List<AdminSurveyDto> surveys = mongoAdminRepository.getSurveys();
        surveys.forEach(el -> el.resolveKorTarget());

        getResponseCount(surveys);
        getMemberInfo(surveys);

        return surveys;
    }

    public void getResponseCount(List<AdminSurveyDto> surveys){
        BatchLoader<AdminSurveyDto, AdminSurveyDto> responseCountBatchLoader =
                surveys1 -> CompletableFuture.supplyAsync(() -> mongoAdminRepository.getResponseCounts(surveys1));

        DataLoader<AdminSurveyDto, AdminSurveyDto> responseCountDataLoader = DataLoaderFactory.newDataLoader(responseCountBatchLoader);

        surveys.stream().map(responseCountDataLoader::load).collect(Collectors.toList());

        responseCountDataLoader.dispatchAndJoin();
    }

    // todo: 데이터 로더는 따로 뺄 수 있을 듯?
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

    @Deprecated
    @Transactional(readOnly = true)
    public List<AdminSurveyDto> getAdminSurveysWhere(Long memberId){
        List<AdminSurveyDto> surveyDtos = queryRepository.getAdminSurveysWhere(memberId);
        surveyDtos.forEach(AdminSurveyDto::resolveKorTarget);

        return surveyDtos;
    }

    /**
     * Responses
     */
    @Deprecated
    @Transactional(readOnly = true)
    public List<AdminResponseDto> getAdminResponsesWhere(Long surveyId){
        return queryRepository.getAdminResponsesWhere(surveyId);
    }

    // todo: 데이터 로더는 따로 뺄 수 있을 듯?
    @Transactional(readOnly = true)
    public List<AdminResponseDto> getMongoResponses(Long surveyId){
        List<AdminResponseDto> responses = mongoAdminRepository.getMongoResponses(surveyId);

        BatchLoader<AdminResponseDto, AdminResponseDto> memberBatchLoader =
                responses1 -> CompletableFuture.supplyAsync(() -> queryRepository.getMemberInfosAboutResponses(responses1));

        DataLoader<AdminResponseDto, AdminResponseDto> memberDataLoader = DataLoaderFactory.newDataLoader(memberBatchLoader);

        responses.stream().map(memberDataLoader::load).collect(Collectors.toList());

        memberDataLoader.dispatchAndJoin();

        return responses;
    }

    /**
     * user log
     */
    @Transactional(readOnly = true)
    public AdminUserLogDto getUserLogsFromSurvey(Long surveyId){
        List<ShortUrlLog> shortUrlLogs = mongoAdminRepository.getUserLogs(surveyId);

        ShortUrl shorturl = urlRepository.findShortUrlByShortUrl(shortUrlLogs.get(0).getUrl());

        return new AdminUserLogDto(shortUrlLogs, shorturl.getReqCount());
    }

    /**
     * Feedback
     */

    @Transactional(readOnly = true)
    public List<AdminFeedbackDto> getFeedbacks(){
        return queryRepository.getFeedbacks();
    }
}
