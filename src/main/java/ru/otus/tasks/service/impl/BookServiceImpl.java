package ru.otus.tasks.service.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.tasks.dao.entity.Author;
import ru.otus.tasks.dao.entity.Book;
import ru.otus.tasks.dao.entity.Genre;
import ru.otus.tasks.dao.repository.AuthorRepository;
import ru.otus.tasks.dao.repository.BookRepository;
import ru.otus.tasks.dao.repository.GenreRepository;
import ru.otus.tasks.service.BookService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@ShellComponent
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    @Override
    @ShellMethod(key = {"create", "cr"}, value = "create book")
    public long donateBook(@ShellOption String bookName, @ShellOption String author, @ShellOption String genre) {
        Book book = createBook(bookName, createAuthors(author), createGenres(genre));
        return bookRepository.save(book).getId();
    }

    @Override
    @Transactional
    @ShellMethod(key = {"take", "t"}, value = "take book")
    public void takeBook(@ShellOption long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Книга не найдена"));
        if (book == null || book.isTaken()) {
            throw new RuntimeException("Книги нет в наличии");
        }
        book.setTaken(true);
    }

    @Override
    @Transactional
    @ShellMethod(key = {"return", "r"}, value = "return book")
    public void returnBook(long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Книга не найдена"));
        book.setTaken(false);
    }

    @Override
    @ShellMethod(key = {"showByName", "sn"}, value = "show by name")
    public List<Book> showBooksByName(@ShellOption String name) {
        return bookRepository.findByName(name);
    }

    @Override
    @ShellMethod(key = {"showAuthors", "a"}, value = "show authors")
    public List<Author> showAuthors() {
        return authorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @ShellMethod(key = {"showByAuthor", "sa"}, value = "show by author")
    public List<Book> showByAuthors(@ShellOption String authorName) {
        Author author = authorRepository.findByName(authorName)
                .orElseThrow(() -> new RuntimeException("Автор не найден"));
        List<Book> books = author.getBooks();
        books.forEach(this::fetchBookChildrenEntities);
        return books;
    }

    @Override
    @ShellMethod(key = {"showGenres", "g"}, value = "show genres")
    public List<Genre> showGenres() {
        return genreRepository.findAll();
    }

    @Override
    @ShellMethod(key = {"showByGenre", "sg"}, value = "show by genre")
    public Book showByGenre(@ShellOption String genre) {
        return genreRepository.findByName(genre)
                .orElseThrow(() -> new RuntimeException("Жанр не найден"))
                .getBook();
    }

    @Override
    @Transactional(readOnly = true)
    @ShellMethod(key = {"showAll", "sall"}, value = "show all")
    public List<Book> showAll() {
        List<Book> books = bookRepository.findAll();
        books.forEach(this::fetchBookChildrenEntities);
        return books;
    }

    @ShellMethod(key = {"delete", "d"}, value = "delete book")
    public void delete(long id) {
        bookRepository.deleteById(id);
    }

    private void fetchBookChildrenEntities(Book book) {
        Hibernate.initialize(book.getGenres());
        Hibernate.initialize(book.getAuthors());
    }

    private Book createBook(String bookName, Set<Author> author, Set<Genre> genre) {
        return Book.builder()
                .name(bookName)
                .authors(author)
                .genres(genre)
                .build();
    }

    private Set<Genre> createGenres(String... genres) {
        return Stream.of(genres)
                .map(genre -> Genre.builder()
                        .name(genre)
                        .build())
                .collect(Collectors.toSet());
    }

    private Set<Author> createAuthors(String... authors) {
        return Stream.of(authors)
                .map(author -> Author.builder()
                        .name(author)
                        .build())
                .collect(Collectors.toSet());
    }
}
