package ru.otus.service.quiz.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import ru.otus.service.io.IOService;
import ru.otus.dao.QuizDao;
import ru.otus.dao.entity.Quiz;
import ru.otus.service.quiz.QuizService;
import ru.otus.service.quiz.dto.Interviewer;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final int requiredPoints;

    private final QuizDao quizDao;

    private final IOService ioService;

    private final MessageSource messageSource;

    @PostConstruct
    public void go() {
        interview();
    }

    @Override
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
        return ioService.in().equals(quiz.getAnswer());
    }
}
