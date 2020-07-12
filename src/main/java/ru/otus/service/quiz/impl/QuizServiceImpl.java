package ru.otus.service.quiz.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.dao.QuizDao;
import ru.otus.dao.entity.Quiz;
import ru.otus.service.io.IOService;
import ru.otus.service.quiz.QuizService;
import ru.otus.service.quiz.dto.Interviewer;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@ShellComponent
public class QuizServiceImpl implements QuizService {

    private final int requiredPoints;

    private final QuizDao quizDao;

    private final IOService ioService;

    private final MessageSource messageSource;

    public QuizServiceImpl(@Value("${required-points}") int requiredPoints, QuizDao quizDao, IOService ioService, MessageSource messageSource) {
        this.requiredPoints = requiredPoints;
        this.quizDao = quizDao;
        this.ioService = ioService;
        this.messageSource = messageSource;
    }

    @Override
    @ShellMethod(key = {"interview", "i"}, value = "pass interview")
    public Interviewer interview() {
        ioService.out(messageSource.getMessage("greeting", new String[]{}, new Locale("ru_RU")));
        String interviewerName = ioService.in();
        List<Quiz> quizList = quizDao.findAll();
        long rightAnswerCount = quizList.stream()
                .map(this::isAnswerRight)
                .filter(anyAnswer -> anyAnswer)
                .count();
        return Interviewer.builder()
                .name(interviewerName)
                .success(rightAnswerCount >= requiredPoints)
                .build();
    }

    private boolean isAnswerRight(Quiz quiz) {
        ioService.out(quiz.getQuestion());
        return Objects.equals(ioService.in(), quiz.getAnswer());
    }
}
