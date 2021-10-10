package com.daangn.survey.domain.survey.model.mapper;

import com.daangn.survey.domain.survey.model.dto.ChoiceDto;
import com.daangn.survey.domain.survey.model.entity.Choice;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChoiceMapper {
    default Choice entityBuilder(ChoiceDto choiceDto, int number){
        return Choice.builder()
                .number(number)
                .value(choiceDto.getValue())
                .build();
    }
}
