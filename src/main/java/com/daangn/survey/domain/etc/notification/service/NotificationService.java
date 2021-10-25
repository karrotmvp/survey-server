package com.daangn.survey.domain.etc.notification.service;

import com.daangn.survey.domain.etc.notification.model.entity.Notification;
import com.daangn.survey.domain.etc.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public void saveNotification(Notification notification){
        notificationRepository.save(notification);
    }
}
