package com.daangn.survey.domain.question.service;

import com.daangn.survey.core.error.ErrorCode;
import com.daangn.survey.core.error.exception.EntityNotFoundException;
import com.daangn.survey.domain.question.model.dto.QuestionDto;
import com.daangn.survey.domain.question.model.entity.QuestionType;
import com.daangn.survey.domain.question.repository.QuestionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionTypeRepository questionTypeRepository;

    @Transactional(readOnly = true)
    public QuestionType findQuestionType(Long questionType){
       return questionTypeRepository.findById(questionType)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.QUESTION_TYPE_NOT_MATCHED));
    }
}
