package ru.otus.tasks.dao.repository.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.tasks.dao.entity.Author;
import ru.otus.tasks.dao.entity.Book;
import ru.otus.tasks.dao.entity.Genre;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@Import(BookJdbcRepository.class)
class BookJdbcRepositoryTest {

    private static final Book EXPECTED = Book.builder()
            .id(1)
            .name("War and peace")
            .author(Author.builder()
                    .id(1)
                    .name("Leo Tolstoy")
                    .build())
            .genre(Genre.builder()
                    .id(1)
                    .name("Historical novel")
                    .build())
            .build();

    private static final List<Book> EXPECTED_LIST = Arrays.asList(EXPECTED, Book.builder()
            .id(2)
            .name("Crime and punishment")
            .author(Author.builder()
                    .name("Fyodor Dostoevsky")
                    .build())
            .genre(Genre.builder()
                    .name("Crime fiction")
                    .build())
            .build());

    @Autowired
    private BookJdbcRepository jdbcRepository;

    @Test
    void create() {
        Book expected = Book.builder().id(3).name("Cay Horstmann").build();
        long id = jdbcRepository.create(expected);
        Book actual = jdbcRepository.findById(id);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.isTaken(), actual.isTaken());
        assertThat(expected).usingRecursiveComparison().isEqualTo(actual);
    }

    @Test
    void findById() {
        Book actual = jdbcRepository.findById(1);
        assertEquals(EXPECTED.getName(), actual.getName());
        assertEquals(EXPECTED.isTaken(), actual.isTaken());
    }

    @Test
    void findByName() {
        Book actual = jdbcRepository.findByName("War and peace").get(0);
        assertEquals(EXPECTED.getName(), actual.getName());
        assertEquals(EXPECTED.isTaken(), actual.isTaken());
    }

    @Test
    void findByAuthor() {
        Book actual = jdbcRepository.findByAuthor("Leo Tolstoy").get(0);
        assertEquals(EXPECTED.getName(), actual.getName());
        assertEquals(EXPECTED.isTaken(), actual.isTaken());
        assertEquals(EXPECTED.getAuthor().getName(), actual.getAuthor().getName());
    }

    @Test
    void findByGenre() {
        Book actual = jdbcRepository.findByGenre("Historical novel").get(0);
        assertEquals(EXPECTED.getName(), actual.getName());
        assertEquals(EXPECTED.isTaken(), actual.isTaken());
        assertEquals(EXPECTED.getGenre().getName(), actual.getGenre().getName());
    }

    @Test
    void findByNameAndAuthorAndGenre() {
        Book actual = jdbcRepository.findByNameAndAuthorAndGenre("War and peace", "Leo Tolstoy", "Historical novel");
        assertThat(actual).usingRecursiveComparison().isEqualTo(EXPECTED);
    }

    @Test
    void findAll() {
        List<Book> actual = jdbcRepository.findAll();
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(actual.get(i).getName(), EXPECTED_LIST.get(i).getName());
            assertEquals(actual.get(i).getId(), EXPECTED_LIST.get(i).getId());
            assertEquals(actual.get(i).isTaken(), EXPECTED_LIST.get(i).isTaken());
        }
    }

    @Test
    void update() {
        Book actual = jdbcRepository.findById(1);
        actual.setTaken(true);
        jdbcRepository.update(actual);
        assertTrue(actual.isTaken());
    }

    @Test
    void delete() {
        jdbcRepository.delete(1);
        assertThrows(EmptyResultDataAccessException.class, () -> jdbcRepository.findById(1));
    }

    @Test
    void count() {
        assertEquals(jdbcRepository.findAll().size(), 2);
    }
}
