package com.daangn.survey.domain.etc.feedback.model.mapper;

import com.daangn.survey.domain.etc.feedback.model.dto.FeedbackDto;
import com.daangn.survey.domain.etc.feedback.model.entity.Feedback;
import com.daangn.survey.domain.member.model.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FeedbackMapper {
    Feedback toEntity(FeedbackDto feedbackDto, Member member);
}
