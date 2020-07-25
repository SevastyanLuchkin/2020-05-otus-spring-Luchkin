package ru.otus.tasks.dao.repository.impl;

import ru.otus.tasks.dao.entity.Book;

import java.util.Arrays;
import java.util.List;

import static ru.otus.tasks.dao.repository.impl.BookJpaRepositoryTest.createAuthors;
import static ru.otus.tasks.dao.repository.impl.BookJpaRepositoryTest.createBook;
import static ru.otus.tasks.dao.repository.impl.BookJpaRepositoryTest.createGenres;

public class TestData {

    public static final Book EXPECTED = createBook("book", createAuthors("author"), createGenres("genre"));

    public static final List<Book> EXPECTED_LIST = Arrays.asList(
            createBook("War and peace", createAuthors("Leo Tolstoy"), createGenres("Historical novel")),
            createBook("Crime and punishment", createAuthors("Fyodor Dostoevsky"), createGenres("Crime fiction")),
            EXPECTED);
}
