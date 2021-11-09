package com.daangn.survey.admin.service;

import com.daangn.survey.admin.dto.AdminResponseDetailDto;
import com.daangn.survey.admin.dto.AdminResponseDto;
import com.daangn.survey.admin.dto.QuestionResponseDto;
import com.daangn.survey.admin.mapper.AdminMapper;
import com.daangn.survey.admin.repository.QueryRepository;
import com.daangn.survey.core.error.ErrorCode;
import com.daangn.survey.core.error.exception.EntityNotFoundException;
import com.daangn.survey.domain.etc.notification.repository.NotificationRepository;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.question.model.entity.Question;
import com.daangn.survey.domain.question.model.entity.QuestionTypeCode;
import com.daangn.survey.domain.question.model.mapper.ChoiceMapper;
import com.daangn.survey.domain.question.repository.ChoiceRepository;
import com.daangn.survey.domain.question.repository.QuestionRepository;
import com.daangn.survey.domain.response.model.entity.ChoiceResponse;
import com.daangn.survey.domain.response.model.entity.SurveyResponse;
import com.daangn.survey.domain.response.model.entity.TextResponse;
import com.daangn.survey.domain.response.model.mapper.ResponseMapper;
import com.daangn.survey.domain.response.repository.ChoiceResponseRepository;
import com.daangn.survey.domain.response.repository.SurveyResponseRepository;
import com.daangn.survey.domain.response.repository.TextResponseRepository;
import com.daangn.survey.domain.survey.model.dto.SurveySummaryDto;
import com.daangn.survey.domain.survey.model.entity.Survey;
import com.daangn.survey.domain.survey.model.mapper.SurveyMapper;
import com.daangn.survey.domain.survey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final SurveyResponseRepository surveyResponseRepository;
    private final SurveyRepository surveyRepository;
    private final TextResponseRepository textResponseRepository;
    private final ChoiceResponseRepository choiceResponseRepository;

    private final ChoiceMapper choiceMapper;
    private final ResponseMapper responseMapper;
    private final AdminMapper adminMapper;
    private final SurveyMapper surveyMapper;

    private final QueryRepository queryRepository;


    // Members
    // Todo: 캐시 기능 넣기
    @Transactional(readOnly = true)
    public List<Member> getAllBizProfiles(){

        return queryRepository.getAllBizProfiles();
    }

    @Transactional(readOnly = true)
    public List<Member> getAllUsers(){

        return queryRepository.getAllUsers();
    }

    // Todo: 유연한 필터 만들기
    @Transactional(readOnly = true)
    public List<Member> getMembersByCondition(){
        return queryRepository.getMembersByCondition();

    }

    // Surveys
    @Transactional(readOnly = true)
    public List<SurveySummaryDto> findAll(){
        List<Survey> surveys = surveyRepository.findAll();

        return surveys.stream().map(surveyMapper::toSummaryDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SurveySummaryDto> findSurveysAboutPublished(){
        return surveyRepository.findSurveysByPublishedAtNotNull().stream().map(surveyMapper::toSummaryDto).collect(Collectors.toList());
    }

    // Responses
    @Transactional(readOnly = true)
    public List<AdminResponseDetailDto> getAdminResponseDetail(SurveyResponse surveyResponse){

        List<AdminResponseDetailDto> adminResponses = new LinkedList<>();

        for(Question question : surveyResponse.getSurvey().getQuestions()){
            AdminResponseDetailDto adminResponseDetailDto = AdminResponseDetailDto.builder().build();

            QuestionResponseDto questionResponse = QuestionResponseDto.builder().question(question.getText()).questionType(question.getQuestionType().getId()).build();

            adminResponseDetailDto.setQuestion(questionResponse);

            switch(QuestionTypeCode.findByNumber(question.getQuestionType().getId())){
                case TEXT_QUESTION:

                    TextResponse textResponse = textResponseRepository.findTextResponseByQuestionIdAndSurveyResponseId(question.getId(), surveyResponse.getId())
                            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
                    adminResponseDetailDto.setResponse(responseMapper.toDtoFromTextResponse(textResponse));
                    break;

                case CHOICE_QUESTION:

                    questionResponse.setChoices(question.getChoices().stream().map(choiceMapper::toChoiceDto).collect(Collectors.toList()));

                    ChoiceResponse choiceResponse = choiceResponseRepository.findChoiceResponseByQuestionIdAndSurveyResponseId(question.getId(), surveyResponse.getId())
                            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
                    adminResponseDetailDto.setResponse(responseMapper.toDtoFromChoiceResponse(choiceResponse));
                    break;
            }

            adminResponses.add(adminResponseDetailDto);
        }

        return adminResponses;

    }

    @Transactional(readOnly = true)
    public List<AdminResponseDto> getAdminResponses(Long surveyId){
        return surveyResponseRepository.findSurveyResponsesBySurveyId(surveyId)
                .stream()
                .map(adminMapper::toAdminResponseDtoFromSurveyResponse)
                .collect(Collectors.toList());
    }
}
