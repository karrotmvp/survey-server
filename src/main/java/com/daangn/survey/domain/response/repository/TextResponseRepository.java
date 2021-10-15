package com.daangn.survey.domain.response.repository;

import com.daangn.survey.domain.response.model.entity.TextResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextResponseRepository extends JpaRepository<TextResponse, Long> {
}
