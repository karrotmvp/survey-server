package com.daangn.survey.domain.etc.feedback.service;

import com.daangn.survey.domain.etc.feedback.model.entity.Feedback;
import com.daangn.survey.domain.etc.feedback.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    @Transactional
    public void saveFeedback(Feedback feedback){
        feedbackRepository.save(feedback);
    }
}
