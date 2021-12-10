package com.daangn.survey.domain.deprecated.response.repository;

import com.daangn.survey.domain.deprecated.response.model.entity.ChoiceResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChoiceResponseRepository extends JpaRepository<ChoiceResponse, Long> {
    Optional<ChoiceResponse> findChoiceResponseByQuestionIdAndSurveyResponseId(Long questionId, Long surveyResponseId);

}
