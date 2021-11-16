package com.daangn.survey.domain.response.repository;

import com.daangn.survey.domain.response.model.entity.TextResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TextResponseRepository extends JpaRepository<TextResponse, Long> {
    Optional<TextResponse> findTextResponseByQuestionIdAndSurveyResponseId(Long questionId, Long surveyResponseId);
}
