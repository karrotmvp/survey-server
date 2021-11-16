package com.daangn.survey.admin.service;

import com.daangn.survey.admin.dto.*;
import com.daangn.survey.admin.repository.QueryRepository;
import com.daangn.survey.core.error.ErrorCode;
import com.daangn.survey.core.error.exception.EntityNotFoundException;
import com.daangn.survey.domain.aggregation.model.individual.AdminResponseDetailDto;
import com.daangn.survey.domain.question.model.entity.Question;
import com.daangn.survey.domain.question.model.entity.QuestionTypeCode;
import com.daangn.survey.domain.question.model.mapper.ChoiceMapper;
import com.daangn.survey.domain.response.model.entity.ChoiceResponse;
import com.daangn.survey.domain.response.model.entity.SurveyResponse;
import com.daangn.survey.domain.response.model.entity.TextResponse;
import com.daangn.survey.domain.response.model.mapper.ResponseMapper;
import com.daangn.survey.domain.response.repository.ChoiceResponseRepository;
import com.daangn.survey.domain.response.repository.TextResponseRepository;
import com.daangn.survey.domain.survey.model.dto.SurveySummaryDto;
import com.daangn.survey.domain.survey.model.entity.Survey;
import com.daangn.survey.domain.survey.model.mapper.SurveyMapper;
import com.daangn.survey.domain.survey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final SurveyRepository surveyRepository;
    private final TextResponseRepository textResponseRepository;
    private final ChoiceResponseRepository choiceResponseRepository;

    private final ChoiceMapper choiceMapper;
    private final ResponseMapper responseMapper;
    private final SurveyMapper surveyMapper;

    private final QueryRepository queryRepository;


    // Members
    // Todo: 캐시 기능 넣기
    @Transactional(readOnly = true)
    public List<AdminMemberDto> getAllBizProfiles(){

        return queryRepository.getAllBizProfiles();
    }

    @Transactional(readOnly = true)
    public List<AdminMemberDto> getAllUsers(){

        return queryRepository.getAllUsers();
    }

    // Todo: 유연한 필터 만들기
    @Transactional(readOnly = true)
    public List<AdminMemberDto> getMembersByCondition(){
        return queryRepository.getMembersByCondition();

    }

    // Surveys
    @Transactional(readOnly = true)
    public List<SurveySummaryDto> findAll(){
        List<Survey> surveys = surveyRepository.findAll();

        return surveys.stream().map(surveyMapper::toSummaryDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AdminSurveyDto> getSurveysAboutPublished(){
        List<AdminSurveyDto> surveyDtos = queryRepository.getAdminSurveysAboutPublished();
        surveyDtos.forEach(AdminSurveyDto::resolveKorTarget);

        return surveyDtos;
    }

    @Transactional(readOnly = true)
    public List<AdminSurveyDto> getAdminSurveyDtos(){
        List<AdminSurveyDto> surveyDtos = queryRepository.getAdminSurveys();
        surveyDtos.forEach(AdminSurveyDto::resolveKorTarget);

        return surveyDtos;
    }

    @Transactional(readOnly = true)
    public List<AdminSurveyDto> getAdminSurveysByMemberId(Long memberId){
        List<AdminSurveyDto> surveyDtos = queryRepository.getAdminSurveysByMemberId(memberId);
        surveyDtos.forEach(AdminSurveyDto::resolveKorTarget);

        return surveyDtos;
    }

    // Responses
    @Transactional(readOnly = true)
    public List<AdminResponseDto> getAdminResponses(Long surveyId){
        return queryRepository.getAdminResponses(surveyId);
    }

    @Transactional(readOnly = true)
    public List<AdminResponseDto> getAllAdminResponses(){
        return queryRepository.getAllAdminResponses();
    }
}
