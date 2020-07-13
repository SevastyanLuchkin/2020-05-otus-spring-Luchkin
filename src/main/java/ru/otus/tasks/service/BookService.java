package ru.otus.tasks.service;

import ru.otus.tasks.dao.entity.Book;

import java.util.List;

public interface BookService {

    void donateBook(String name, String author, String genre);

    void returnBook(long id);

    void takeBook(String name, String author, String genre);

    List<Book> showBooksByName(String name);

    List<Book> showBooksByAuthor(String author);

    List<Book> showBooksByGenre(String author);

    List<Book> showAll();
}
