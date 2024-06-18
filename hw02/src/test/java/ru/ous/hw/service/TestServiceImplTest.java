package ru.ous.hw.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.TestService;
import ru.otus.hw.service.TestServiceImpl;

import java.util.Arrays;
import java.util.List;

@DisplayName("Класс TestServiceImpl")
public class TestServiceImplTest {

    private final IOService ioService = Mockito.mock(IOService.class);

    private final QuestionDao questionDao = Mockito.mock(QuestionDao.class);

    private StringBuilder stringBuilder;

    private TestService testService;

    @BeforeEach
    public void setUpStreams() {

        this.testService = new TestServiceImpl(ioService, questionDao);
        this.stringBuilder = new StringBuilder();
    }

    @Test
    @DisplayName("корректно выводит данные вопросов")
    void shouldHaveCorrectOutput() {

        Mockito.doAnswer(this::mockIoServicePrintEmptyLine)
            .when(ioService)
            .printEmptyLine();
        Mockito.doAnswer(this::mockIoServicePrintLine)
            .when(ioService)
            .printLine(Mockito.any());

        Mockito.doAnswer(this::mockIoServicePrintFormattedLine)
            .when(ioService)
            .printFormattedLine(Mockito.any(), Mockito.any(Object[].class));

        Mockito.doReturn(List.of(createSimpleQuestion()))
            .when(questionDao)
            .findAll();

        testService.executeTest();

        Assertions.assertEquals(
            """
            
            Please answer the questions below
            
            
            Question 1:
            test
            
            1. test
            2. test
            """,
            this.stringBuilder.toString()
        );
    }

    private Question createSimpleQuestion() {

        return Question.builder()
            .text("test")
            .answers(List.of(
                createSimpleAnswer(),
                createSimpleAnswer()
            ))
            .build();
    }

    private Answer createSimpleAnswer() {

        return Answer.builder()
            .text("test")
            .isCorrect(true)
            .build();
    }

    private Object mockIoServicePrintEmptyLine(InvocationOnMock invocation) {

        this.stringBuilder.append("\n");
        return null;
    }

    private Object mockIoServicePrintLine(InvocationOnMock invocation) {

        this.stringBuilder.append(invocation.getArgument(0, String.class)).append("\n");
        return null;
    }

    private Object mockIoServicePrintFormattedLine(InvocationOnMock invocation) {

        this.stringBuilder.append(
            String.format(
                invocation.getArgument(0, String.class),
                Arrays.copyOfRange(invocation.getArguments(), 1, invocation.getArguments().length)
            )
        );
        this.stringBuilder.append("\n");
        return null;
    }
}
