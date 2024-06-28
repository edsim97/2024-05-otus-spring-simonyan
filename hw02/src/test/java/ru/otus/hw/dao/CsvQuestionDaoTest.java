package ru.otus.hw.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;
import java.util.stream.IntStream;

@DisplayName("Класс CsvQuestionDao")
@ExtendWith(MockitoExtension.class)
public class CsvQuestionDaoTest {

    private final static String QUESTIONS_FILE_NAME = "questions.csv";

    @Mock
    private TestFileNameProvider fileNameProvider;

    @InjectMocks
    private CsvQuestionDao questionDao;

    @Test
    @DisplayName("корректно читает вопросы")
    void shouldReadQuestions() {

        Mockito.doReturn(QUESTIONS_FILE_NAME).when(fileNameProvider).getTestFileName();

        final List<Question> questions = questionDao.findAll();

        Assertions.assertEquals(3, questions.size());

        for (int i = 0, bound = questions.size(); i < bound; i++) {

            final Question question = questions.get(i);
            Assertions.assertEquals(String.format("Test%s", i), question.text());
            Assertions.assertEquals(2, question.answers().size());
        }

        final List<Answer> answers = questions.stream()
            .map(Question::answers)
            .flatMap(List::stream)
            .toList();

        IntStream.range(0, answers.size()).forEach(idx -> {

            final Answer answer = answers.get(idx);
            Assertions.assertEquals(String.format("test%s", idx), answer.text());
        });
    }
}
