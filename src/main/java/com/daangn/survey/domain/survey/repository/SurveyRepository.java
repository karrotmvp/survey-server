package com.daangn.survey.domain.survey.repository;

import com.daangn.survey.domain.survey.model.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> findSurveysByMemberIdOrderByCreatedAtDesc(Long memberId);
}
