package com.daangn.survey.domain.survey.model.mapper;

import com.daangn.survey.domain.question.model.entity.Choice;
import com.daangn.survey.domain.question.model.entity.Question;
import com.daangn.survey.domain.question.model.entity.QuestionType;
import com.daangn.survey.domain.survey.model.dto.SurveyDto;
import com.daangn.survey.domain.survey.model.entity.Survey;
import com.daangn.survey.domain.survey.model.entity.Target;
import net.bytebuddy.build.Plugin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
class SurveyMapperTest {
    @Autowired
    private SurveyMapper surveyMapper;

    @Test
    void toSummaryDto() {
        QuestionType questionTypeChoice = QuestionType.builder().id(3L).name("단일객관식").build();

        QuestionType questionTypeIntroduce = QuestionType.builder().id(1L).name("소개").build();

        Choice choice1 = Choice.builder().id(1L).number(1).value("선택지 값").build();
        Choice choice2 = Choice.builder().id(2L).number(2).value("선택지 값2").build();


        Question questionIntroduce = Question.builder()
                                    .id(2L)
                                    .questionType(questionTypeIntroduce)
                                    .text("소개 글")
                                    .number(0)
                                    .description("소개글 설명")
                                    .build();

        Question questionChoice = Question.builder()
                .id(3L)
                .questionType(questionTypeChoice)
                .text("객관식 질문")
                .number(1)
                .description("객관식 짊문이에요")
                .choices(Arrays.asList(choice1, choice2))
                .build();


        Survey survey = Survey.builder().id(2L).title("설문 제목").target(Target.CUSTOMER.getCode()).questions(Arrays.asList(questionIntroduce, questionChoice)).build();

        SurveyDto surveyDto = surveyMapper.toDetailDto(survey);

        assertEquals(surveyDto.getSurveyId(), survey.getId());
        assertEquals(surveyDto.getTitle(), survey.getTitle());
        assertEquals(surveyDto.getTarget(), survey.getTarget());
        assertEquals(surveyDto.getQuestions().size(), survey.getQuestions().size());
        assertEquals(surveyDto.getQuestions().get(1).getChoices().size(), survey.getQuestions().get(1).getChoices().size());
    }

    @Test
    void toDetailDto() {
    }

    @Test
    void entityBuilder() {

    }
}