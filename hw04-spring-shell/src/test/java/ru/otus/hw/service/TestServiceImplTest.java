package ru.otus.hw.service;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willReturn;

@AllArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("Класс TestServiceImpl")
@SpringBootTest
public class TestServiceImplTest {

    private LocalizedIOService ioService;

    private QuestionDao questionDao;

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

        willReturn(createQuestions()).given(questionDao).findAll();

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
