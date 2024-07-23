package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printLineLocalized("TestService.answer.the.questions");
        ioService.printLine("");

        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (int i = 0; i < questions.size(); i++) {

            var question = questions.get(i);
            var isAnswerValid = askQuestion(i + 1, question);
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }

    private boolean askQuestion(int questionNumber, Question question) {

        ioService.printEmptyLine();
        ioService.printFormattedLineLocalized("TestService.question", questionNumber);
        ioService.printLine(question.text());
        ioService.printEmptyLine();
        printAnswers(question.answers());

        final int answerCount = question.answers().size();
        var answer = ioService.readIntForRangeWithPromptLocalized(
            1,
            answerCount,
            "TestService.answer.choose",
            "TestService.answer.choose.error"
        );

        return question.answers().get(answer - 1).isCorrect();
    }

    private void printAnswers(List<Answer> answers) {

        IntStream.range(0, answers.size()).forEachOrdered(index -> printAnswer(index + 1, answers.get(index)));
    }

    private void printAnswer(int answerNumber, Answer answer) {

        ioService.printFormattedLine("%s. %s", answerNumber, answer.text());
    }
}
