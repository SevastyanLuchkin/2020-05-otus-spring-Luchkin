package ru.otus.spring.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.config.QuizProperties;
import ru.otus.dao.QuizDao;
import ru.otus.dao.entity.Quiz;
import ru.otus.dao.impl.QuizDaoCsv;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static ru.otus.spring.dao.impl.TestData.EXPECTED;
import static ru.otus.spring.dao.impl.TestData.EXPECTED_LIST;

@DisplayName("Тест QuizDaoCsv")
class QuizDaoCsvTest {

    private QuizProperties quizProperties = new QuizProperties("src/test/resources/quiz.csv", 0, 1, 2, ";");

    private QuizDao quizDao = new QuizDaoCsv(quizProperties);

    @Test
    @DisplayName("Тест findById")
    void findByIdSuccessTest() {
        Quiz actual = quizDao.findById(1).get();
        assertThat(actual).as("id").isEqualTo(EXPECTED)
                .as("question").isEqualTo(EXPECTED)
                .as("answer").isEqualTo(EXPECTED);
    }

    @Test
    @DisplayName("Тест findById")
    void findByIdNotFountTest() {
        Throwable throwable = catchThrowable(() -> quizDao.findById(10).get());
        assertThat(throwable)
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("No value present");
    }

    @Test
    @DisplayName("Тест findAll")
    void findAllTest() {
        List<Quiz> all = quizDao.findAll();
        assertThat(all).hasSize(5);

        for (int i = 0; i < all.size(); i++) {
            Quiz actual = all.get(i);
            Quiz expected = EXPECTED_LIST.get(i);
            assertThat(actual).as("id").isEqualTo(expected)
                    .as("question").isEqualTo(expected)
                    .as("answer").isEqualTo(expected);
        }
    }
}
