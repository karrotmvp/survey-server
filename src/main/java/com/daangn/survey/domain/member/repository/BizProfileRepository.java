package com.daangn.survey.domain.member.repository;

import com.daangn.survey.domain.member.model.entity.BizProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BizProfileRepository extends JpaRepository<BizProfile, Long> {
    Optional<BizProfile> findBizProfileByBusinessId(String businessId);
}
