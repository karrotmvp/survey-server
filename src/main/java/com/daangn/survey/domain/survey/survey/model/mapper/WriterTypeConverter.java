package com.daangn.survey.domain.survey.survey.model.mapper;

import com.daangn.survey.domain.survey.survey.model.entity.WriterType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class WriterTypeConverter implements AttributeConverter<WriterType, String> {

    @Override
    public String convertToDatabaseColumn(WriterType writerType) {
        return writerType.getCode();
    }

    @Override
    public WriterType convertToEntityAttribute(String string) {
        return Stream.of(WriterType.values())
                .filter(c -> c.getValue() == string)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}