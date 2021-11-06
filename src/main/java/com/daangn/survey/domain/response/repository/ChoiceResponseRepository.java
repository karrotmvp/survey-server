package com.daangn.survey.domain.response.repository;

import com.daangn.survey.domain.response.model.entity.ChoiceResponse;
import com.daangn.survey.domain.response.model.entity.TextResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChoiceResponseRepository extends JpaRepository<ChoiceResponse, Long> {
    List<ChoiceResponse> findChoiceResponsesByQuestionIdAndChoiceId(Long questionId, Long choiceId);

    int countChoiceResponsesByChoiceId(Long choiceId);

    List<ChoiceResponse> findChoiceResponsesBySurveyResponseId(Long surveyResponseId);

    Optional<ChoiceResponse> findChoiceResponseBySurveyResponseId(Long surveyResponseId);

}
