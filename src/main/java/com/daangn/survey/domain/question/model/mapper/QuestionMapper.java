package com.daangn.survey.domain.question.model.mapper;

import com.daangn.survey.domain.question.model.dto.QuestionDto;
import com.daangn.survey.domain.question.model.entity.Question;
import com.daangn.survey.domain.question.model.entity.QuestionType;
import com.daangn.survey.domain.survey.model.entity.Survey;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionMapper {

    default Question entityBuilder(QuestionDto questionDto, Survey survey, int number, QuestionType questionType){
        return Question.builder()
                .survey(survey)
                .questionType(questionType)
                .number(number)
                .text(questionDto.getText())
                .build();
    }
}
