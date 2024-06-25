package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {

        ioService.printEmptyLine();
        ioService.printFormattedLine("Please answer the questions below%n");
        // Получить вопросы из дао и вывести их с вариантами ответов
        final List<Question> questions = questionDao.findAll();
        var testResult = new TestResult(student);
        IntStream.range(0, questions.size())
            .forEach(index -> this.askQuestion(index + 1, questions.get(index)));

        for (var question: questions) {
            var isAnswerValid = false; // Задать вопрос, получить ответ
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }

    private void askQuestion(int questionNumber, Question question) {

        ioService.printEmptyLine();
        ioService.printFormattedLine("Question %d:", questionNumber);
        ioService.printLine(question.text());
        ioService.printEmptyLine();
        printAnswers(question.answers());
    }

    private void printAnswers(List<Answer> answers) {

        IntStream.range(0, answers.size()).forEachOrdered(index -> printAnswer(index + 1, answers.get(index)));
    }

    private void printAnswer(int answerNumber, Answer answer) {

        ioService.printFormattedLine("%s. %s", answerNumber, answer.text());
    }
}
