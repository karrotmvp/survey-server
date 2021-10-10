package com.daangn.survey.domain.survey.model.mapper;

import com.daangn.survey.domain.survey.model.dto.QuestionDto;
import com.daangn.survey.domain.survey.model.entity.Question;
import com.daangn.survey.domain.survey.model.entity.QuestionType;
import com.daangn.survey.domain.survey.model.entity.Survey;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionMapper {

    default Question entityBuilder(QuestionDto questionDto, int number, QuestionType questionType, Survey survey){
        return Question.builder()
                .survey(survey)
                .questionType(questionType)
                .number(number)
                .text(questionDto.getText())
                .build();
    }
}
