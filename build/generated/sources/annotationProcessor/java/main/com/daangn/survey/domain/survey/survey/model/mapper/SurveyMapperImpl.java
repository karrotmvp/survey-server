package com.daangn.survey.domain.survey.survey.model.mapper;

import com.daangn.survey.domain.member.model.dto.BizProfileDto;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.response.model.entity.SurveyResponse;
import com.daangn.survey.domain.survey.question.model.dto.QuestionDto;
import com.daangn.survey.domain.survey.question.model.entity.Question;
import com.daangn.survey.domain.survey.question.model.mapper.QuestionMapper;
import com.daangn.survey.domain.survey.survey.model.dto.SurveyBriefDto;
import com.daangn.survey.domain.survey.survey.model.dto.SurveyDto;
import com.daangn.survey.domain.survey.survey.model.dto.SurveyRequestDto;
import com.daangn.survey.domain.survey.survey.model.dto.SurveySummaryDto;
import com.daangn.survey.domain.survey.survey.model.entity.Survey;
import com.daangn.survey.mongo.survey.SurveyMongo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-12-03T21:10:14+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.0.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
@Component
public class SurveyMapperImpl implements SurveyMapper {

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public SurveySummaryDto toSummaryDto(Survey survey) {
        if ( survey == null ) {
            return null;
        }

        SurveySummaryDto surveySummaryDto = new SurveySummaryDto();

        surveySummaryDto.setSurveyId( survey.getId() );
        surveySummaryDto.setTitle( survey.getTitle() );
        surveySummaryDto.setCreatedAt( survey.getCreatedAt() );

        surveySummaryDto.setResponseCount( survey.getSurveyResponses().size() );
        surveySummaryDto.setTarget( survey.convertTarget() );

        return surveySummaryDto;
    }

    @Override
    public SurveyDto toDetailDto(Survey survey) {
        if ( survey == null ) {
            return null;
        }

        SurveyDto surveyDto = new SurveyDto();

        surveyDto.setSurveyId( survey.getId() );
        surveyDto.setTitle( survey.getTitle() );
        surveyDto.setTarget( survey.getTarget() );
        surveyDto.setQuestions( questionListToQuestionDtoList( survey.getQuestions() ) );
        surveyDto.setCreatedAt( survey.getCreatedAt() );

        return surveyDto;
    }

    @Override
    public Survey toEntity(SurveyRequestDto surveyRequestDto, Member member) {
        if ( surveyRequestDto == null && member == null ) {
            return null;
        }

        List<Question> questions = null;
        String title = null;
        int target = 0;
        if ( surveyRequestDto != null ) {
            questions = questionDtoListToQuestionList( surveyRequestDto.getQuestions() );
            title = surveyRequestDto.getTitle();
            target = surveyRequestDto.getTarget();
        }
        Member member1 = null;
        if ( member != null ) {
            member1 = member;
        }

        Long id = null;
        LocalDateTime publishedAt = null;
        boolean isDeleted = false;
        List<SurveyResponse> surveyResponses = null;

        Survey survey = new Survey( id, member1, title, target, publishedAt, isDeleted, questions, surveyResponses );

        return survey;
    }

    @Override
    public SurveyBriefDto toSurveyBriefDtoWithMember(Survey survey, BizProfileDto bizProfileDto, int estimatedTime) {
        if ( survey == null && bizProfileDto == null ) {
            return null;
        }

        SurveyBriefDto surveyBriefDto = new SurveyBriefDto();

        if ( survey != null ) {
            surveyBriefDto.setCreatedAt( survey.getCreatedAt() );
            surveyBriefDto.setTitle( survey.getTitle() );
        }
        if ( bizProfileDto != null ) {
            surveyBriefDto.setBizProfile( bizProfileDto );
            List<String> list = bizProfileDto.getCoverImageUrls();
            if ( list != null ) {
                surveyBriefDto.setCoverImageUrls( new ArrayList<String>( list ) );
            }
        }
        surveyBriefDto.setEstimatedTime( estimatedTime );
        surveyBriefDto.setQuestionCount( survey.getQuestions().size() );
        surveyBriefDto.setTarget( survey.convertTarget() );

        return surveyBriefDto;
    }

    @Override
    public SurveyBriefDto toSurveyBriefDtoFromSurveyMongo(SurveyMongo survey, BizProfileDto bizProfileDto, int estimatedTime) {
        if ( survey == null && bizProfileDto == null ) {
            return null;
        }

        SurveyBriefDto surveyBriefDto = new SurveyBriefDto();

        if ( survey != null ) {
            surveyBriefDto.setCreatedAt( survey.getCreatedAt() );
            surveyBriefDto.setTitle( survey.getTitle() );
        }
        if ( bizProfileDto != null ) {
            surveyBriefDto.setBizProfile( bizProfileDto );
            List<String> list = bizProfileDto.getCoverImageUrls();
            if ( list != null ) {
                surveyBriefDto.setCoverImageUrls( new ArrayList<String>( list ) );
            }
        }
        surveyBriefDto.setEstimatedTime( estimatedTime );
        surveyBriefDto.setQuestionCount( survey.getQuestions().size() );
        surveyBriefDto.setTarget( survey.convertTarget() );

        return surveyBriefDto;
    }

    protected List<QuestionDto> questionListToQuestionDtoList(List<Question> list) {
        if ( list == null ) {
            return null;
        }

        List<QuestionDto> list1 = new ArrayList<QuestionDto>( list.size() );
        for ( Question question : list ) {
            list1.add( questionMapper.toQuestionDto( question ) );
        }

        return list1;
    }

    protected List<Question> questionDtoListToQuestionList(List<QuestionDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Question> list1 = new ArrayList<Question>( list.size() );
        for ( QuestionDto questionDto : list ) {
            list1.add( questionMapper.toEntity( questionDto ) );
        }

        return list1;
    }
}
