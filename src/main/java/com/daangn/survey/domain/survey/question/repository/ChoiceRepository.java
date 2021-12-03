package com.daangn.survey.domain.survey.question.repository;

import com.daangn.survey.domain.survey.question.model.entity.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {
}
