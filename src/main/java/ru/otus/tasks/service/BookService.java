package ru.otus.tasks.service;

import org.springframework.shell.standard.ShellMethod;
import ru.otus.tasks.dao.entity.Author;
import ru.otus.tasks.dao.entity.Book;

import java.util.List;

public interface BookService {

    long donateBook(String name, String author, String genre);

    void returnBook(long id);

    void takeBook(String name, String author, String genre);

    List<Book> showBooksByName(String name);

    @ShellMethod(key = {"showByAuthor", "sa"}, value = "show by author")
    List<Author> showAuthors();

    Book showByAuthors(String author);

    @ShellMethod(key = {"showGenres", "g"}, value = "show genres")
    List<Author> showGenres();

    Book showByGenres(String author);

    List<Book> showAll();
}
