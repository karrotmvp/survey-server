package com.daangn.survey.domain.response.repository;

import com.daangn.survey.domain.response.model.entity.SurveyResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Long> {
    boolean existsSurveyResponseBySurveyIdAndMemberId(Long surveyId, Long memberId);
}
