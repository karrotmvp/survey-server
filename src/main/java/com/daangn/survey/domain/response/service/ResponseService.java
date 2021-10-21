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
import org.springframework.security.access.prepost.PreAuthorize;
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

    // TODO: 한 번 응답한 유저는 응답 불가
    @PreAuthorize("hasRole('ROLE_USER')")
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
    public void getAggregation(Long surveyId){
        Survey survey = surveyRepository.findByIdAndIsDeletedFalse(surveyId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.SURVEY_NOT_FOUND));

        /**
         * 질문 리스트 가져오기
         * 질문에 해당하는 선택지 가정과
         * 선택지와 질문 id를 통해 객관식
         */
        List<Question> questions = survey.getQuestions();

        for(Question question : questions){
            if(question.getQuestionType().getId() == QuestionTypeCode.TEXT_QUESTION.getNumber()){

                textResponseRepository.findTextResponsesByQuestionId(question.getId());

            } else if(question.getQuestionType().getId() == QuestionTypeCode.CHOICE_QUESTION.getNumber()){

                for(Choice choice : question.getChoices()){
                    choice.getValue();

                    int count = choiceResponseRepository.countChoiceResponsesByChoiceId(choice.getId());
                }
            }
        }

        int responseCount = survey.getSurveyResponses().size(); // 좋은 방법은 아님

        survey.getSurveyResponses();


        /**
         * 설문의 질문 별로 답변 리스트 가져오기
         * 객관식의 경우, 통계를 위해
         */

    }
}
