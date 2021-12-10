package com.daangn.survey.domain.deprecated.response.repository;

import com.daangn.survey.domain.deprecated.response.model.entity.SurveyResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Long> {
    boolean existsSurveyResponseBySurveyIdAndMemberId(Long surveyId, Long memberId);
}
