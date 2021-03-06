package com.daangn.survey.domain.deprecated.survey.question.model.mapper;

import com.daangn.survey.core.error.ErrorCode;
import com.daangn.survey.core.error.exception.BusinessException;
import com.daangn.survey.domain.deprecated.survey.question.model.dto.QuestionDto;
import com.daangn.survey.domain.deprecated.survey.question.model.entity.Question;
import com.daangn.survey.domain.deprecated.survey.question.model.entity.QuestionType;
import com.daangn.survey.domain.deprecated.survey.survey.model.entity.Survey;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {ChoiceMapper.class, QuestionType.class})
public interface QuestionMapper {

    @Mapping(target = "questionId", source = "question.id")
    @Mapping(target = "questionType", expression = "java(question.getQuestionType().getId())")
    QuestionDto toQuestionDto(Question question);

    @Mapping(target = "questionType", expression = "java(questionDto.convertToQuestionType())")
    Question toEntity(QuestionDto questionDto);

    default Question entityBuilder(QuestionDto questionDto, Survey survey, int number, QuestionType questionType){
        if(!questionDto.checkQuestionTypeCondition())
            throw new BusinessException(ErrorCode.QUESTION_TYPE_CONDITION_NOT_MATCHED);

        return Question.builder()
                .survey(survey)
                .questionType(questionType)
                .number(number)
                .text(questionDto.getText())
                .description(questionDto.getDescription())
                .build();
    }
}
