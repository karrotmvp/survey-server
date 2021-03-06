package com.daangn.survey.domain.deprecated.survey.question.repository;

import com.daangn.survey.domain.deprecated.survey.question.model.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
