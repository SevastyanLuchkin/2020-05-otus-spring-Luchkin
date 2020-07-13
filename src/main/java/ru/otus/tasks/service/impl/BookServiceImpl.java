package ru.otus.tasks.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Service;
import ru.otus.tasks.dao.entity.Author;
import ru.otus.tasks.dao.entity.Book;
import ru.otus.tasks.dao.entity.Genre;
import ru.otus.tasks.dao.repository.AuthorRepository;
import ru.otus.tasks.dao.repository.BookRepository;
import ru.otus.tasks.dao.repository.GenreRepository;
import ru.otus.tasks.service.BookService;

import java.util.List;

@Service
@ShellComponent
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    @Override
    @ShellMethod(key = {"return", "r"}, value = "return book")
    public void returnBook(long id) {
        Book book = bookRepository.findById(id);
        book.setTaken(false);
        bookRepository.update(book);
    }

    @Override
    @ShellMethod(key = {"donate", "d"}, value = "donate book")
    public void donateBook(@ShellOption String bookName, @ShellOption String author, @ShellOption String genre) {
        Book book = createBook(bookName, author, genre);
        long bookId = bookRepository.create(book);
        authorRepository.create(book.getAuthor(), bookId);
        genreRepository.create(book.getGenre(), bookId);
    }

    @Override
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

    private Book createBook(String bookName, String author, String genre) {
        return Book.builder()
                .name(bookName)
                .author(Author.builder()
                        .name(author)
                        .build())
                .genre(Genre.builder()
                        .name(genre)
                        .build())
                .build();
    }
}
