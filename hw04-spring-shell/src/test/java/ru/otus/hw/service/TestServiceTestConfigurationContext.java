package ru.otus.hw.service;

import org.mockito.Mockito;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.*;

@SpringBootConfiguration
public class TestServiceTestConfigurationContext {

    @MockBean
    private LocalizedIOService ioService;

    @MockBean
    private QuestionDao questionDao;

    @Bean
    TestServiceImpl testService() {

        final List<String> lines = new ArrayList<>();

        willReturn(questions()).given(questionDao).findAll();

        willDoNothing().given(ioService).printEmptyLine();
        willDoNothing().given(ioService).printLine(anyString());
        willDoNothing().given(ioService).printLineLocalized(anyString());
        willDoNothing().given(ioService).printFormattedLine(anyString());
        willDoNothing().given(ioService).printFormattedLineLocalized(anyString());

        willReturn(1).given(ioService).readIntForRange(anyInt(), anyInt(), anyString());
        willReturn(1).given(ioService).readIntForRangeLocalized(anyInt(), anyInt(), anyString());
        willReturn(1)
            .given(ioService)
            .readIntForRangeWithPrompt(anyInt(), anyInt(), anyString(), anyString());
        willReturn(1)
            .given(ioService)
            .readIntForRangeWithPromptLocalized(anyInt(), anyInt(), anyString(), anyString());

        return new TestServiceImpl(ioService, questionDao);
    }

    @Bean
    List<Question> questions() {

        final Question question = Question.builder()
            .text("test")
            .answers(List.of(
                Answer.builder()
                    .text("test1")
                    .isCorrect(true)
                    .build(),
                Answer.builder()
                    .text("test2")
                    .isCorrect(false)
                    .build()
            ))
            .build();

        return List.of(question);
    }
}
