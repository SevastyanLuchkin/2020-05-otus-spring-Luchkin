package ru.otus.tasks.dao.repository.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.tasks.dao.entity.Author;
import ru.otus.tasks.dao.repository.AuthorRepository;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.tasks.dao.repository.impl.TestData.EXPECTED_BOOK;

@DataJpaTest
@Import(AuthorJpaRepository.class)
class AuthorJpaRepositoryTest {

    @Autowired
    TestEntityManager tem;

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void findByName() {
        Author author = authorRepository.findByName(EXPECTED_BOOK.getAuthors().stream().findAny().get().getName());
        assertEquals(author.getName(), EXPECTED_BOOK.getAuthors().stream().findAny().get().getName());
        assertEquals(author.getBook().getName(), EXPECTED_BOOK.getName());
    }
}