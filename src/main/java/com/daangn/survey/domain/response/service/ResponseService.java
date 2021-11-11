package com.daangn.survey.domain.response.service;

import com.daangn.survey.core.error.ErrorCode;
import com.daangn.survey.core.error.exception.EntityNotFoundException;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.question.model.entity.Choice;
import com.daangn.survey.domain.question.model.entity.Question;
import com.daangn.survey.domain.question.model.entity.QuestionTypeCode;
import com.daangn.survey.domain.question.repository.ChoiceRepository;
import com.daangn.survey.domain.question.repository.QuestionRepository;
import com.daangn.survey.domain.response.model.dto.CommonResponseDto;
import com.daangn.survey.domain.response.model.dto.SurveyResponseDto;
import com.daangn.survey.domain.response.model.entity.ChoiceResponse;
import com.daangn.survey.domain.response.model.entity.SurveyResponse;
import com.daangn.survey.domain.response.model.entity.TextResponse;
import com.daangn.survey.domain.response.repository.ChoiceResponseRepository;
import com.daangn.survey.domain.response.repository.SurveyResponseRepository;
import com.daangn.survey.domain.response.repository.TextResponseRepository;
import com.daangn.survey.domain.survey.model.entity.Survey;
import com.daangn.survey.domain.survey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponseService {
    private final SurveyResponseRepository surveyResponseRepository;
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
    private final TextResponseRepository textResponseRepository;
    private final ChoiceResponseRepository choiceResponseRepository;

    @Transactional
    public void saveResponse(Member member, SurveyResponseDto surveyResponseDto){
        List<ChoiceResponse> choiceResponses = new LinkedList<>();
        List<TextResponse> textResponses = new LinkedList<>();

        Survey survey = surveyRepository.findById(surveyResponseDto.getSurveyId())
                                                                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.SURVEY_NOT_FOUND));

        SurveyResponse surveyResponse = SurveyResponse.builder().member(member).survey(survey).build();

        for(CommonResponseDto commonResponseDto : surveyResponseDto.getResponses()){

            Question question = questionRepository.findById(commonResponseDto.getQuestionId())
                    .orElseThrow(() -> new EntityNotFoundException(ErrorCode.QUESTION_NOT_FOUND));

            if(commonResponseDto.getQuestionType() == QuestionTypeCode.CHOICE_QUESTION.getNumber()){
                Choice choice = choiceRepository.findById((commonResponseDto.getChoiceId()))
                        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CHOICE_NOT_FOUND));

                ChoiceResponse choiceResponse = ChoiceResponse.builder().choice(choice).question(question).surveyResponse(surveyResponse).build();

                choiceResponses.add(choiceResponse);
            } else {
                TextResponse textResponse = TextResponse.builder().question(question).answer(commonResponseDto.getAnswer()).surveyResponse(surveyResponse).build();

                textResponses.add(textResponse);
            }
        }

        textResponseRepository.saveAll(textResponses);
        choiceResponseRepository.saveAll(choiceResponses);
        surveyResponseRepository.save(surveyResponse);
    }

    @Transactional(readOnly = true)
    public boolean respondedPrevious(Member member, Long surveyId) {
        return surveyResponseRepository.existsSurveyResponseBySurveyIdAndMemberId(surveyId, member.getId());
    }

    @Transactional(readOnly = true)
    public SurveyResponse getSurveyResponse(Long responseId){
        return surveyResponseRepository.findById(responseId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.RESPONSE_NOT_FOUND));
    }
}
