package ru.otus.tasks.service;

import ru.otus.tasks.dao.entity.Author;
import ru.otus.tasks.dao.entity.Book;

import java.util.List;

public interface BookService {

    long donateBook(String name, String author, String genre);

    void returnBook(long id);

    void takeBook(String name, String author, String genre);

    List<Book> showBooksByName(String name);

    List<Author> showAuthors(String author);

    List<Book> showAll();
}
