package com.daangn.survey.domain.question.model.mapper;

import com.daangn.survey.domain.question.model.dto.QuestionDto;
import com.daangn.survey.domain.question.model.entity.Question;
import com.daangn.survey.domain.question.model.entity.QuestionType;
import com.daangn.survey.domain.survey.model.entity.Survey;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {ChoiceMapper.class})
public interface QuestionMapper {

    @Mapping(target = "questionType", expression = "java(question.getQuestionType().getId())")
    QuestionDto toQuestionDto(Question question);

    default Question entityBuilder(QuestionDto questionDto, Survey survey, int number, QuestionType questionType){
        return Question.builder()
                .survey(survey)
                .questionType(questionType)
                .number(number)
                .text(questionDto.getText())
                .build();
    }
}
