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
import ru.otus.tasks.dao.repository.BookRepository;
import ru.otus.tasks.service.BookService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@ShellComponent
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    @Transactional
    @ShellMethod(key = {"return", "r"}, value = "return book")
    public void returnBook(long id) {
        Book book = bookRepository.findById(id);
        book.setTaken(false);
        bookRepository.update(book);
    }

    @Override
    @ShellMethod(key = {"donate", "d"}, value = "donate book")
    public long donateBook(@ShellOption String bookName, @ShellOption String author, @ShellOption String genre) {
        Book book = createBook(bookName, createAuthors(author), createGenres(genre));
        return bookRepository.create(book);
    }

    @Override
    @Transactional
    @ShellMethod(key = {"take", "t"}, value = "take book")
    public void takeBook(@ShellOption String name, @ShellOption String author, @ShellOption String genre) {
        Book book = bookRepository.findByNameAndAuthorAndGenre(name, author, genre);
        isBookAvailable(book);
        book.setTaken(true);
        bookRepository.update(book);
    }

    @Override
    @ShellMethod(key = {"showByName", "sn"}, value = "show by name")
    public List<Book> showBooksByName(@ShellOption String name) {
        return bookRepository.findByName(name);
    }

    @Override
    @ShellMethod(key = {"showByAuthor", "sa"}, value = "show by author")
    public List<Book> showBooksByAuthor(@ShellOption String author) {
        return bookRepository.findByAuthor(author);
    }

    @Override
    @ShellMethod(key = {"showByGenre", "sg"}, value = "show by genre")
    public List<Book> showBooksByGenre(@ShellOption String genre) {
        return bookRepository.findByGenre(genre);
    }

    @Override
    @ShellMethod(key = {"showAll", "sall"}, value = "show all")
    public List<Book> showAll() {
        return bookRepository.findAll();
    }

    private void isBookAvailable(Book book) {
        if (book == null || book.isTaken()) {
            throw new RuntimeException("Книги нет в наличии");
        }
    }

    private Book createBook(String bookName, List<Author> author, List<Genre> genre) {
        return Book.builder()
                .name(bookName)
                .author(author)
                .genre(genre)
                .build();
    }

    private List<Genre> createGenres(String... genres) {
        return Stream.of(genres)
                .map(genre -> Genre.builder()
                        .name(genre)
                        .build())
                .collect(Collectors.toList());
    }

    private List<Author> createAuthors(String... authors) {
        return Stream.of(authors)
                .map(author -> Author.builder()
                        .name(author)
                        .build())
                .collect(Collectors.toList());
    }
}
