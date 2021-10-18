package com.daangn.survey.domain.etc.feedback.repository;

import com.daangn.survey.domain.etc.feedback.model.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
