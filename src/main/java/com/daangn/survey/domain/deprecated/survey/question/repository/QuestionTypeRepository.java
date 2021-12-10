package com.daangn.survey.domain.deprecated.survey.question.repository;

import com.daangn.survey.domain.deprecated.survey.question.model.entity.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionTypeRepository extends JpaRepository<QuestionType, Long> {
}
