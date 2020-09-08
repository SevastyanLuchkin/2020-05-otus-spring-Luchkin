package ru.otus.tasks.dao.repository;

import ru.otus.tasks.dao.entity.Book;

import java.util.List;

public interface BookRepository {

    long create(Book book);

    Book findById(long id);

    List<Book> findByName(String name);

    Book findByNameAndAuthorAndGenre(String book, String author, String genre);

    List<Book> findAll();

    void update(Book book);

    void delete(long id);
}
