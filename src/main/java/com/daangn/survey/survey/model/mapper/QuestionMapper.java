package com.daangn.survey.survey.model.mapper;

import com.daangn.survey.survey.model.dto.QuestionDto;
import com.daangn.survey.survey.model.entity.Question;
import com.daangn.survey.survey.model.entity.QuestionType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionMapper {

    default Question entityBuilder(QuestionDto questionDto, int number, QuestionType questionType){
        return Question.builder()
                .questionType(questionType)
                .number(number)
                .text(questionDto.getText())
                .build();
    }
}
