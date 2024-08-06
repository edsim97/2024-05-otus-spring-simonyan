package ru.otus.hw.dao;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import ru.otus.hw.config.TestFileNameProvider;

import static org.mockito.BDDMockito.given;

@SpringBootConfiguration
public class CsvQuestionDaoTestConfigurationContext {

    private final static String QUESTIONS_FILE_NAME = "questions.csv";

    @MockBean
    private TestFileNameProvider fileNameProvider;

    @Bean
    CsvQuestionDao csvQuestionDao() {

        given(fileNameProvider.getTestFileName()).willReturn(QUESTIONS_FILE_NAME);

        return new CsvQuestionDao(fileNameProvider);
    }
}
