package ru.otus.spring.service.quiz.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.dao.QuizDao;
import ru.otus.dao.entity.Quiz;
import ru.otus.service.io.IOService;
import ru.otus.service.quiz.QuizService;
import ru.otus.service.quiz.dto.Interviewer;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@ExtendWith(SpringExtension.class)
class QuizServiceImplTest {

    @MockBean
    private QuizDao quizDao;

    @MockBean
    private IOService ioService;

    @Autowired
    private QuizService quizService;

    @BeforeEach
    void beforeEach() {
        Mockito.when(quizDao.findAll())
                .thenReturn(Arrays.asList(Quiz.builder()
                                .id(1)
                                .question("What’s your favorite color?")
                                .answer("darcula")
                                .build(),
                        Quiz.builder()
                                .id(2)
                                .question("What makes you happiest?")
                                .answer("java")
                                .build()));
    }

    @Test
    void interviewPassedTest() {
        Mockito.when(ioService.in())
                .thenReturn("James Gosling")
                .thenReturn("darcula")
                .thenReturn("java");

        Interviewer expected = Interviewer.builder()
                .name("James Gosling")
                .success(true)
                .build();

        Interviewer actual = quizService.interview();
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void interviewFailedTest() {
        Mockito.when(ioService.in())
                .thenReturn("Egor Bugaenko")
                .thenReturn("decorator")
                .thenReturn("decorator");
        Interviewer expected = Interviewer.builder()
                .name("Egor Bugaenko")
                .success(false)
                .build();
        Interviewer actual = quizService.interview();
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
