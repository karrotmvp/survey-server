package com.daangn.survey.domain.etc.notification.model.mapper;

import com.daangn.survey.domain.etc.feedback.model.dto.FeedbackDto;
import com.daangn.survey.domain.etc.feedback.model.entity.Feedback;
import com.daangn.survey.domain.etc.notification.model.dto.NotificationDto;
import com.daangn.survey.domain.etc.notification.model.entity.Notification;
import com.daangn.survey.domain.member.model.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "member", source = "member")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "isNotifying", source = "notificationDto.notifying")
    Notification toEntity(NotificationDto notificationDto, Member member, String type);
}
