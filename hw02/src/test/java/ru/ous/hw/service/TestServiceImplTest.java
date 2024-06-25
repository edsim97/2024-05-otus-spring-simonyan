package ru.ous.hw.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.TestService;
import ru.otus.hw.service.TestServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@DisplayName("Класс TestServiceImpl")
@ExtendWith(MockitoExtension.class)
public class TestServiceImplTest {

    @Mock
    private IOService ioService;

    @Mock
    private QuestionDao questionDao;

    @InjectMocks
    private TestServiceImpl testService;

    @Test
    @DisplayName("корректно выводит данные вопросов")
    void shouldHaveCorrectOutput() {

        final List<String> lines = new ArrayList<>();

        Mockito.doNothing()
            .when(ioService)
            .printEmptyLine();

        Mockito.doAnswer(invocation -> lines.add(invocation.getArgument(0, String.class)))
            .when(ioService)
            .printLine(Mockito.any());

        Mockito.doAnswer(invocation ->
            lines.add(
                String.format(
                    invocation.getArgument(0, String.class),
                    Arrays.copyOfRange(invocation.getArguments(), 1, invocation.getArguments().length)
                )
            )
        )
            .when(ioService)
            .printFormattedLine(Mockito.any(), Mockito.any(Object[].class));

        Mockito.doReturn(1)
            .when(ioService)
            .readIntForRangeWithPrompt(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString());

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

        final Student student = new Student("Firstname", "Lastname");

        this.testService.executeTestFor(student);

        Assertions.assertEquals(
            "Please answer the questions below Question 1: test 1. test1 2. test2",
            String.join(" ", lines)
        );
    }
}
