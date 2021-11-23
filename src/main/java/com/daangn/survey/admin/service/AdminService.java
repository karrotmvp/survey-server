package com.daangn.survey.admin.service;

import com.daangn.survey.admin.dto.*;
import com.daangn.survey.admin.repository.QueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final QueryRepository queryRepository;


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
