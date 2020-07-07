package ru.otus.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.dao.QuizDao;
import ru.otus.service.io.IOService;
import ru.otus.service.quiz.QuizService;
import ru.otus.service.quiz.impl.QuizServiceImpl;

@Configuration
@RequiredArgsConstructor
public class QuizServiceConfig {

    private final QuizDao quizDao;

    private final IOService ioService;

    private final MessageSource messageSource;

    @Value("${required-points}")
    private int requiredPoints;

    @Bean
    public QuizService quizService() {
        return new QuizServiceImpl(requiredPoints, quizDao, ioService, messageSource);
    }
}
