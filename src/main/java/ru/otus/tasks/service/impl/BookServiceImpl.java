package ru.otus.tasks.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.tasks.dao.entity.Book;
import ru.otus.tasks.dao.repository.BookRepository;
import ru.otus.tasks.service.BookService;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@ShellComponent
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    @ShellMethod(key = {"create", "cr"}, value = "create book")
    public long donateBook(@ShellOption String bookName, @ShellOption String author, @ShellOption String genre) {
        Book book = createBook(bookName, Collections.singletonList(author), Collections.singletonList(genre));
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
        bookRepository.save(book);
    }

    @Override
    @Transactional
    @ShellMethod(key = {"return", "r"}, value = "return book")
    public void returnBook(long id) {
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
    public Set<String> showAuthors() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(Book::getAuthors)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true)
    @ShellMethod(key = {"showByAuthor", "sa"}, value = "show by author")
    public List<Book> showByAuthors(@ShellOption String authorName) {
        return bookRepository.findByAuthorName(authorName);
    }

    @Override
    @ShellMethod(key = {"showGenres", "g"}, value = "show genres")
    public Set<String> showGenres() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(Book::getGenres)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    @Override
    @ShellMethod(key = {"showByGenre", "sg"}, value = "show by genre")
    public List<Book> showByGenre(@ShellOption String genre) {
        return bookRepository.findByGenreName(genre);
    }

    @Override
    @Transactional(readOnly = true)
    @ShellMethod(key = {"showAll", "sall"}, value = "show all")
    public List<Book> showAll() {
        return bookRepository.findAll();
    }

    @ShellMethod(key = {"delete", "d"}, value = "delete book")
    public void delete(long id) {
        bookRepository.deleteById(id);
    }

    private Book createBook(String bookName, List<String> authors, List<String> genres) {
        return Book.builder()
                .id(UUID.randomUUID().getLeastSignificantBits())
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
                        .id(UUID.randomUUID().getLeastSignificantBits())
                        .name("book")
                        .authors(Collections.singletonList("author"))
                        .genres(Collections.singletonList("genre"))
                        .build(),
                Book.builder()
                        .id(UUID.randomUUID().getLeastSignificantBits())
                        .name("War and Peace")
                        .authors(Collections.singletonList("Tolstoy"))
                        .genres(Collections.singletonList("Historical novel"))
                        .build(),
                Book.builder()
                        .id(UUID.randomUUID().getLeastSignificantBits())
                        .name("Crime and punishment")
                        .authors(Collections.singletonList("Dostoevsky"))
                        .genres(Collections.singletonList("Crime fiction"))
                        .build()
        ).collect(Collectors.toList());
        bookRepository.saveAll(books);
    }
}
