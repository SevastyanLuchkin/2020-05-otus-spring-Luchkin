package ru.otus.tasks.service;

import ru.otus.tasks.dao.entity.Book;

import java.util.List;
import java.util.Set;

public interface BookService {

    long donateBook(String name, String author, String genre);

    void takeBook(long id);

    void returnBook(long id);

    List<Book> showBooksByName(String name);

    Set<String> showAuthors();

    List<Book> showByAuthors(String author);

    Set<String> showGenres();

    List<Book> showByGenre(String author);

    List<Book> showAll();
}
