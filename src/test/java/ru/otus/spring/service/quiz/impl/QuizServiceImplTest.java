package ru.otus.spring.service.quiz.impl;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.config.QuizProperties;
import ru.otus.dao.QuizDao;
import ru.otus.dao.impl.QuizDaoCsv;
import ru.otus.service.io.IOService;
import ru.otus.service.io.impl.IOServiceImpl;
import ru.otus.service.quiz.QuizService;
import ru.otus.service.quiz.dto.Interviewer;
import ru.otus.service.quiz.impl.QuizServiceImpl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.spring.service.quiz.impl.TestData.TEST_FAILED;
import static ru.otus.spring.service.quiz.impl.TestData.TEST_PASSED;

class QuizServiceImplTest {

    private QuizProperties quizProperties = new QuizProperties("src/test/resources/quiz.csv", 0, 1, 2, ";");
    ReloadableResourceBundleMessageSource messageSource = getMessageSource();

    private QuizDao quizDao = new QuizDaoCsv(quizProperties);

    @Test
    void interviewPassedTest() {
        Interviewer expected = Interviewer.builder()
                .name("James Gosling")
                .success(true)
                .build();
        IOService ioService = new IOServiceImpl(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(TEST_PASSED.getBytes()))), System.out);
        QuizService quizService = new QuizServiceImpl(2, quizDao, ioService, messageSource);
        Interviewer actual = quizService.interview();

        assertThat(actual).as("name").isEqualTo(expected)
                .as("success").isEqualTo(expected);
    }

    @Test
    void interviewFailedTest() {
        Interviewer expected = Interviewer.builder()
                .name("Egor Bugaenko")
                .success(false)
                .build();
        IOService ioService = new IOServiceImpl(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(TEST_FAILED.getBytes()))), System.out);
        QuizService quizService = new QuizServiceImpl(2, quizDao, ioService, messageSource);
        Interviewer actual = quizService.interview();

        assertThat(actual).as("name").isEqualTo(expected)
                .as("success").isEqualTo(expected);
    }

    private ReloadableResourceBundleMessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/i18n/bundle");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
