package com.daangn.survey.admin.repository;

import com.daangn.survey.admin.dto.AdminSurveyDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QueryRepositoryTest {

    @Autowired
    private QueryRepository queryRepository;

    @Test
    void getSurveysAboutPublished() {
        List<AdminSurveyDto> list = queryRepository.getSurveysAboutPublished();

        System.out.println(list);

    }
}