package ru.otus.tasks.dao.repository.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.tasks.dao.entity.Genre;
import ru.otus.tasks.dao.repository.GenreRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.otus.tasks.dao.repository.impl.TestData.EXPECTED_BOOK;

@DataJpaTest
@Import(GenreJpaRepository.class)
class GenreJpaRepositoryTest {

    @Autowired
    TestEntityManager tem;

    @Autowired
    GenreRepository genreRepository;

    @Test
    void findByName() {
        Genre author = genreRepository.findByName(EXPECTED_BOOK.getGenres().stream().findAny().get().getName());
        assertEquals(author.getName(), EXPECTED_BOOK.getGenres().stream().findAny().get().getName());
        assertEquals(author.getBook().getName(), EXPECTED_BOOK.getName());
    }
}