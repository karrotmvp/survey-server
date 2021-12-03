package com.daangn.survey.domain.survey.question.model.mapper;

import com.daangn.survey.domain.survey.question.model.dto.ChoiceDto;
import com.daangn.survey.domain.survey.question.model.entity.Choice;
import com.daangn.survey.domain.survey.question.model.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChoiceMapper {

    @Mapping(target = "choiceId", source = "choice.id")
    ChoiceDto toChoiceDto(Choice choice);

    default Choice entityBuilder(ChoiceDto choiceDto,Question question, int number){
        return Choice.builder()
                .question(question)
                .number(number)
                .value(choiceDto.getValue())
                .build();
    }
}
