package com.daangn.survey.domain.response.service;

import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.response.model.dto.SurveyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResponseService {

    @Transactional
    public void saveResponse(Member member, SurveyResponseDto surveyResponseDto){

    }
}
