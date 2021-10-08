package com.daangn.survey.survey.service;

import com.daangn.survey.survey.model.entity.Survey;
import com.daangn.survey.survey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;

    @Transactional
    public void saveSurvey(Survey survey){
        surveyRepository.save(survey);
    }
}
