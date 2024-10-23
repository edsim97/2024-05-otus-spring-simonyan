package ru.otus.hw.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@DisplayName("Класс CsvQuestionDao")
@SpringBootTest
public class CsvQuestionDaoTest {

    private final static String QUESTIONS_FILE_NAME = "questions.csv";

    @SpyBean
    private TestFileNameProvider fileNameProvider;

    @Autowired
    private CsvQuestionDao questionDao;

    @Test
    @DisplayName("корректно читает вопросы")
    void shouldReadQuestions() {

        doReturn(QUESTIONS_FILE_NAME).when(fileNameProvider).getTestFileName();

        final List<Question> questions = questionDao.findAll();

        assertEquals(3, questions.size());

        for (int i = 0, bound = questions.size(); i < bound; i++) {

            final Question question = questions.get(i);
            assertEquals(String.format("Test%s", i), question.text());
            assertEquals(2, question.answers().size());
        }

        final List<Answer> answers = questions.stream()
            .map(Question::answers)
            .flatMap(List::stream)
            .toList();

        for (int answerIdx = 0, answersSize = answers.size(); answerIdx < answersSize; answerIdx++) {

            final Answer answer = answers.get(answerIdx);
            assertEquals(String.format("test%s", answerIdx), answer.text());
        }
    }
}
