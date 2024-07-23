package ru.otus.hw.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс CsvQuestionDao")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CsvQuestionDaoTest {

    @Autowired
    private CsvQuestionDao questionDao;

    @Test
    @DisplayName("корректно читает вопросы")
    void shouldReadQuestions() {

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
