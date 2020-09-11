package ru.otus.tasks.service;

import ru.otus.tasks.dao.entity.Author;
import ru.otus.tasks.dao.entity.Book;
import ru.otus.tasks.dao.entity.Genre;

import java.util.List;

public interface BookService {

    long donateBook(String name, String author, String genre);

    void takeBook(long id);

    void returnBook(long id);

    List<Book> showBooksByName(String name);

    List<Author> showAuthors();

    List<Book> showByAuthors(String author);

    List<Genre> showGenres();

    Book showByGenre(String author);

    List<Book> showAll();
}
