package com.daangn.survey.domain.question.model.mapper;

import com.daangn.survey.domain.question.model.dto.ChoiceDto;
import com.daangn.survey.domain.question.model.dto.QuestionDto;
import com.daangn.survey.domain.question.model.entity.Choice;
import com.daangn.survey.domain.question.model.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChoiceMapper {

    ChoiceDto toChoiceDto(Choice choice);

    default Choice entityBuilder(ChoiceDto choiceDto,Question question, int number){
        return Choice.builder()
                .question(question)
                .number(number)
                .value(choiceDto.getValue())
                .build();
    }
}
