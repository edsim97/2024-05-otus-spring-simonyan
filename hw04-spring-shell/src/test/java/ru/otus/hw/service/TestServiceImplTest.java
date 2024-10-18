package ru.otus.hw.service;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@AllArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("Класс TestServiceImpl")
@SpringBootTest
public class TestServiceImplTest {

    @MockBean
    private LocalizedIOService ioService;

    @MockBean
    private QuestionDao questionDao;

    @Autowired
    private TestServiceImpl testService;

    @Test
    @DisplayName("отдаёт корректный результат")
    void shouldHaveCorrectResult() {

        this.setupTestService();

        final Student student = new Student("Firstname", "Lastname");
        final TestResult testResult = this.testService.executeTestFor(student);

        Assertions.assertEquals(
            student,
            testResult.getStudent()
        );

        Assertions.assertEquals(
            1,
            testResult.getRightAnswersCount()
        );

        Assertions.assertIterableEquals(
            createQuestions(),
            testResult.getAnsweredQuestions()
        );
    }

    private void setupTestService() {

        doReturn(createQuestions()).when(questionDao).findAll();

        doNothing().when(ioService).printEmptyLine();
        doNothing().when(ioService).printEmptyLine();
        doNothing().when(ioService).printLine(anyString());
        doNothing().when(ioService).printLineLocalized(anyString());
        doNothing().when(ioService).printFormattedLine(anyString());
        doNothing().when(ioService).printFormattedLineLocalized(anyString());

        doReturn(1).when(ioService).readIntForRange(anyInt(), anyInt(), anyString());
        doReturn(1).when(ioService).readIntForRangeLocalized(anyInt(), anyInt(), anyString());
        doReturn(1)
            .when(ioService)
            .readIntForRangeWithPrompt(anyInt(), anyInt(), anyString(), anyString());
        doReturn(1)
            .when(ioService)
            .readIntForRangeWithPromptLocalized(anyInt(), anyInt(), anyString(), anyString());
    }

    private List<Question> createQuestions() {

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
