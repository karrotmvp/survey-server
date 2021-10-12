package com.daangn.survey.domain.survey.repository;

import com.daangn.survey.domain.survey.model.dto.SurveyDto;
import com.daangn.survey.domain.survey.model.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> findSurveysByMemberIdAndAndIsDeletedFalseOrderByCreatedAtDesc(Long memberId);

    /**
     * select q.survey_id, title, description, q.question_id, text, choice_id,c.number, value
     * from survey
     *     right outer join question q on survey.survey_id = q.survey_id
     *     left outer join choice c on q.question_id = c.question_id
     * where q.survey_id = 2
     *
     * select *
     * from survey
     *     right join question q on survey.survey_id = q.survey_id
     *     left join choice c on q.question_id = c.question_id
     */

    Optional<Survey> findByIdAndIsDeletedFalse(Long surveyId);

    Optional<Survey> findSurveyByIdAndMemberIdAndIsDeletedFalse(Long surveyId, Long memberId);
}
