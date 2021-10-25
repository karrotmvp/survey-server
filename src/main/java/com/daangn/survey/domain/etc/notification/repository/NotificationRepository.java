package com.daangn.survey.domain.etc.notification.repository;

import com.daangn.survey.domain.etc.notification.model.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
