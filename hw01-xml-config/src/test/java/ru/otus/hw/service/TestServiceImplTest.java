package ru.otus.hw.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.TestService;
import ru.otus.hw.service.TestServiceImpl;

import java.util.Arrays;
import java.util.List;

@DisplayName("Класс TestServiceImpl")
@ExtendWith(MockitoExtension.class)
public class TestServiceImplTest {

    @Mock
    private IOService ioService;

    @Mock
    private QuestionDao questionDao;

    @Test
    @DisplayName("корректно выводит данные вопросов")
    void shouldHaveCorrectOutput() {

        final TestService testService = new TestServiceImpl(this.ioService, this.questionDao);
        final StringBuilder stringBuilder = new StringBuilder();


        Mockito.doAnswer(i -> stringBuilder.append(System.lineSeparator()))
            .when(ioService)
            .printEmptyLine();

        Mockito.doAnswer(
            invocation -> stringBuilder.append(invocation.getArgument(0, String.class)).append(System.lineSeparator())
        )
            .when(ioService)
            .printLine(Mockito.any());

        Mockito.doAnswer(invocation ->
            stringBuilder.append(
                String.format(
                    invocation.getArgument(0, String.class),
                    Arrays.copyOfRange(invocation.getArguments(), 1, invocation.getArguments().length)
                )
            )
            .append("\n")
        )
            .when(ioService)
            .printFormattedLine(Mockito.any(), Mockito.any(Object[].class));

        final Question question = Question.builder()
            .text("test")
            .answers(List.of(
                Answer.builder()
                    .text("test1")
                    .isCorrect(true)
                    .build(),
                Answer.builder()
                    .text("test2")
                    .isCorrect(true)
                    .build()
            ))
            .build();

        Mockito.doReturn(List.of(question))
            .when(questionDao)
            .findAll();

        testService.executeTest();

        Assertions.assertEquals(
            """
            
            Please answer the questions below
            
            
            Question 1:
            test
            
            1. test1
            2. test2
            """,
            stringBuilder.toString()
        );
    }
}
