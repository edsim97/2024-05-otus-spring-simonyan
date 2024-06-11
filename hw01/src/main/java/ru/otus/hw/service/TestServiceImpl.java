package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {

        ioService.printEmptyLine();
        ioService.printFormattedLine("Please answer the questions below%n");
        // Получить вопросы из дао и вывести их с вариантами ответов
        final List<Question> questions = questionDao.findAll();
        IntStream.range(0, questions.size())
            .forEach(index -> this.askQuestion(index, questions.get(index)));
    }

    private void askQuestion(int index, Question question) {

        ioService.printEmptyLine();
        ioService.printFormattedLine("Question %d:", index);
        ioService.printLine(question.text());
        ioService.printEmptyLine();
        printAnswers(question.answers());
    }

    private void printAnswers(List<Answer> answers) {

        IntStream.range(0, answers.size()).forEachOrdered(index -> printAnswer(index, answers.get(index)));
    }

    private void printAnswer(int index, Answer answer) {

        ioService.printFormattedLine("%s. %s", index, answer.text());
    }
}
