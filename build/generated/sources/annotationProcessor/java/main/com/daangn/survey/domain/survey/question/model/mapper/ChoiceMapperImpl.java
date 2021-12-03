package com.daangn.survey.domain.survey.question.model.mapper;

import com.daangn.survey.domain.survey.question.model.dto.ChoiceDto;
import com.daangn.survey.domain.survey.question.model.entity.Choice;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-12-03T20:47:27+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.0.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
@Component
public class ChoiceMapperImpl implements ChoiceMapper {

    @Override
    public ChoiceDto toChoiceDto(Choice choice) {
        if ( choice == null ) {
            return null;
        }

        ChoiceDto choiceDto = new ChoiceDto();

        choiceDto.setChoiceId( choice.getId() );
        choiceDto.setValue( choice.getValue() );

        return choiceDto;
    }
}
