package com.daangn.survey.domain.etc.feedback.model.mapper;

import com.daangn.survey.domain.etc.feedback.model.dto.FeedbackDto;
import com.daangn.survey.domain.etc.feedback.model.entity.Feedback;
import com.daangn.survey.domain.member.model.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FeedbackMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "member", source = "member")
    Feedback toEntity(FeedbackDto feedbackDto, Member member);

}
