package ru.otus.tasks.service.impl;

import lombok.RequiredArgsConstructor;
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

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;

@Service
@ShellComponent
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    @Override
    @ShellMethod(key = {"create", "cr"}, value = "create book")
    public String donateBook(@ShellOption String bookName, @ShellOption String author, @ShellOption String genre) {
        Book book = createBook(bookName,
                Collections.singletonList(Author.builder()
                        .name(author)
                        .build()),
                Collections.singletonList(Genre.builder()
                        .name(genre)
                        .build()));
        authorRepository.saveAll(book.getAuthors());
        genreRepository.saveAll(book.getGenres());
        return bookRepository.save(book).getId();
    }

    @Override
    @Transactional
    @ShellMethod(key = {"take", "t"}, value = "take book")
    public void takeBook(@ShellOption String id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Книга не найдена"));
        if (book == null || book.isTaken()) {
            throw new RuntimeException("Книги нет в наличии");
        }
        book.setTaken(true);
        bookRepository.save(book);
    }

    @Override
    @Transactional
    @ShellMethod(key = {"return", "r"}, value = "return book")
    public void returnBook(@ShellOption String id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Книга не найдена"));
        book.setTaken(false);
        bookRepository.save(book);
    }

    @Override
    @ShellMethod(key = {"showByName", "sn"}, value = "show by name")
    public List<Book> showBooksByName(@ShellOption String name) {
        return bookRepository.findByName(name);
    }

    @Override
    @ShellMethod(key = {"showAuthors", "a"}, value = "show authors")
    public Set<Author> showAuthors() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(Book::getAuthors)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true)
    @ShellMethod(key = {"showByAuthor", "sa"}, value = "show by author id")
    public List<Book> showByAuthors(@ShellOption String authorId) {
        return bookRepository.findByAuthorId(authorId);
    }

    @Override
    @ShellMethod(key = {"showGenres", "g"}, value = "show genres")
    public Set<Genre> showGenres() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(Book::getGenres)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    @Override
    @ShellMethod(key = {"showByGenre", "sg"}, value = "show by genre id")
    public List<Book> showByGenre(@ShellOption String genreId) {
        return bookRepository.findByGenreId(genreId);
    }

    @Override
    @Transactional(readOnly = true)
    @ShellMethod(key = {"showAll", "sall"}, value = "show all")
    public List<Book> showAll() {
        return bookRepository.findAll();
    }

    @ShellMethod(key = {"delete", "d"}, value = "delete book")
    public void delete(String id) {
        bookRepository.deleteById(id);
    }

    private Book createBook(String bookName, List<Author> authors, List<Genre> genres) {
        return Book.builder()
                .name(bookName)
                .authors(authors)
                .genres(genres)
                .build();
    }

    @PostConstruct
    public void mongoMigratingSystemInit() {
        if (bookRepository.findAll().size() != 0) {
            return;
        }
        List<Book> books = Stream.of(
                Book.builder()
                        .name("book")
                        .authors(singletonList(Author.builder().name("author").build()))
                        .genres(singletonList(Genre.builder().name("genre").build()))
                        .build(),
                Book.builder()
                        .name("War and Peace")
                        .authors(singletonList(Author.builder().name("Tolstoy").build()))
                        .genres(singletonList(Genre.builder().name("Historical novel").build()))
                        .build(),
                Book.builder()
                        .name("Crime and punishment")
                        .authors(singletonList(Author.builder().name("Dostoevsky").build()))
                        .genres(singletonList(Genre.builder().name("Crime fiction").build()))
                        .build()
        ).collect(Collectors.toList());
        books.forEach(book -> {
            authorRepository.saveAll(book.getAuthors());
            genreRepository.saveAll(book.getGenres());
        });
        bookRepository.saveAll(books);
    }
}
