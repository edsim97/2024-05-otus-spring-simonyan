package ru.otus.hw.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

@DisplayName("Класс TestServiceImpl")
@SpringBootTest
public class TestServiceImplTest {

    @Autowired
    private TestServiceImpl testService;

    @Autowired
    private List<Question> questions;

    @Test
    @DisplayName("отдаёт корректный результат")
    void shouldHaveCorrectResult() {

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
            questions,
            testResult.getAnsweredQuestions()
        );
    }
}
