package com.daangn.survey.domain.survey.service;

import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.survey.model.dto.SurveyRequestDto;

public interface SurveyService {
    void saveSurvey(Member member, SurveyRequestDto surveyRequestDto);
}
