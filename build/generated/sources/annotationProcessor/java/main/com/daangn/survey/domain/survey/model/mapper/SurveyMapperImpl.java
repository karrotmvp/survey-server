package com.daangn.survey.domain.survey.model.mapper;

import com.daangn.survey.domain.survey.model.dto.SurveySummaryDto;
import com.daangn.survey.domain.survey.model.dto.SurveySummaryDto.SurveySummaryDtoBuilder;
import com.daangn.survey.domain.survey.model.entity.Survey;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-11T00:55:43+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.2.jar, environment: Java 11.0.8 (Oracle Corporation)"
)
@Component
public class SurveyMapperImpl implements SurveyMapper {

    @Override
    public SurveySummaryDto toSummaryDto(Survey survey) {
        if ( survey == null ) {
            return null;
        }

        SurveySummaryDtoBuilder surveySummaryDto = SurveySummaryDto.builder();

        surveySummaryDto.title( survey.getTitle() );
        surveySummaryDto.createdAt( survey.getCreatedAt() );

        surveySummaryDto.responseCount( survey.getSurveyResponses().size() );

        return surveySummaryDto.build();
    }
}
