package ru.otus.tasks.dao.repository;

import ru.otus.tasks.dao.entity.Book;

import java.util.List;
import java.util.Map;

public interface BookRepository {

    long create(Book book);

    Book findById(long id);

    List<Book> findByName(String name);

    Map<Long, Book> findByAuthor(String author);

    List<Book> findByGenre(String genre);

    Book findByNameAndAuthorAndGenre(String book, String author, String genre);

    List<Book> findAll();

    void update(Book book);

    void delete(long id);
}
