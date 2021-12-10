package com.daangn.survey.domain.deprecated.response.repository;

import com.daangn.survey.domain.deprecated.response.model.entity.TextResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TextResponseRepository extends JpaRepository<TextResponse, Long> {
    Optional<TextResponse> findTextResponseByQuestionIdAndSurveyResponseId(Long questionId, Long surveyResponseId);
}
