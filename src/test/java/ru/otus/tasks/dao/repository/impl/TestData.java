package ru.otus.tasks.dao.repository.impl;

import ru.otus.tasks.dao.entity.Book;

import java.util.Arrays;
import java.util.List;

import static ru.otus.tasks.dao.repository.impl.BookJpaRepositoryTest.*;

class TestData {

    static final Book EXPECTED_BOOK = createBook("book", createAuthors("author"), createGenres("genre"));

    static final List<Book> EXPECTED_BOOKS = Arrays.asList(
            createBook("War and peace", createAuthors("Leo Tolstoy"), createGenres("Historical novel")),
            createBook("Crime and punishment", createAuthors("Fyodor Dostoevsky"), createGenres("Crime fiction")),
            EXPECTED_BOOK);
}
