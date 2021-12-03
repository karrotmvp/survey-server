package com.daangn.survey.domain.survey.question.model.mapper;

import com.daangn.survey.domain.survey.question.model.dto.ChoiceDto;
import com.daangn.survey.domain.survey.question.model.dto.QuestionDto;
import com.daangn.survey.domain.survey.question.model.entity.Choice;
import com.daangn.survey.domain.survey.question.model.entity.Question;
import com.daangn.survey.domain.survey.question.model.entity.QuestionType;
import com.daangn.survey.domain.survey.survey.model.entity.Survey;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-12-03T20:47:26+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.0.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
@Component
public class QuestionMapperImpl implements QuestionMapper {

    @Autowired
    private ChoiceMapper choiceMapper;

    @Override
    public QuestionDto toQuestionDto(Question question) {
        if ( question == null ) {
            return null;
        }

        QuestionDto questionDto = new QuestionDto();

        questionDto.setQuestionId( question.getId() );
        questionDto.setText( question.getText() );
        questionDto.setDescription( question.getDescription() );
        questionDto.setChoices( choiceListToChoiceDtoList( question.getChoices() ) );

        questionDto.setQuestionType( question.getQuestionType().getId() );

        return questionDto;
    }

    @Override
    public Question toEntity(QuestionDto questionDto) {
        if ( questionDto == null ) {
            return null;
        }

        List<Choice> choices = null;
        String text = null;
        String description = null;

        choices = choiceDtoListToChoiceList( questionDto.getChoices() );
        text = questionDto.getText();
        description = questionDto.getDescription();

        QuestionType questionType = questionDto.convertToQuestionType();
        Long id = null;
        Survey survey = null;
        Integer number = null;

        Question question = new Question( id, survey, questionType, number, text, description, choices );

        return question;
    }

    protected List<ChoiceDto> choiceListToChoiceDtoList(List<Choice> list) {
        if ( list == null ) {
            return null;
        }

        List<ChoiceDto> list1 = new ArrayList<ChoiceDto>( list.size() );
        for ( Choice choice : list ) {
            list1.add( choiceMapper.toChoiceDto( choice ) );
        }

        return list1;
    }

    protected Choice choiceDtoToChoice(ChoiceDto choiceDto) {
        if ( choiceDto == null ) {
            return null;
        }

        String value = null;

        value = choiceDto.getValue();

        Long id = null;
        Question question = null;
        Integer number = null;

        Choice choice = new Choice( id, question, number, value );

        return choice;
    }

    protected List<Choice> choiceDtoListToChoiceList(List<ChoiceDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Choice> list1 = new ArrayList<Choice>( list.size() );
        for ( ChoiceDto choiceDto : list ) {
            list1.add( choiceDtoToChoice( choiceDto ) );
        }

        return list1;
    }
}
