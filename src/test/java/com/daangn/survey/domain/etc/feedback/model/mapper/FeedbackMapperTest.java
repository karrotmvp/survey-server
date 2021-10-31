package com.daangn.survey.domain.etc.feedback.model.mapper;

import com.daangn.survey.common.annotation.MockUser;
import com.daangn.survey.domain.etc.feedback.model.dto.FeedbackDto;
import com.daangn.survey.domain.etc.feedback.model.entity.Feedback;
import com.daangn.survey.domain.member.model.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FeedbackMapperTest {

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Test
    void toEntity() {
        FeedbackDto feedbackDto = FeedbackDto.builder().answer("answer").question("question").build();
        Member member = Member.builder().id(1L).daangnId("test").build();

        Feedback feedback = feedbackMapper.toEntity(feedbackDto, member);

        assertEquals(feedback.getMember().getDaangnId(), member.getDaangnId());
        assertEquals(feedback.getQuestion(), feedbackDto.getQuestion());
        assertEquals(feedback.getAnswer(), feedbackDto.getAnswer());

    }
}