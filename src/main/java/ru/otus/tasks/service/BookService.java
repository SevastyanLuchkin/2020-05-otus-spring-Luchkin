package ru.otus.tasks.service;

import ru.otus.tasks.dao.entity.Author;
import ru.otus.tasks.dao.entity.Book;
import ru.otus.tasks.dao.entity.Genre;

import java.util.List;
import java.util.Set;

public interface BookService {

    String donateBook(String name, String author, String genre);

    void takeBook(String id);

    void returnBook(String id);

    List<Book> showBooksByName(String name);

    Set<Author> showAuthors();

    List<Book> showByAuthors(String author);

    Set<Genre> showGenres();

    List<Book> showByGenre(String author);

    List<Book> showAll();
}
