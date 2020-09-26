package ru.otus.tasks.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.tasks.dao.entity.Book;
import ru.otus.tasks.service.BookService;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.create(book), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Book> findAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping("/{bookId}")
    public Book findById(@PathVariable Long bookId) {
        return bookService.findById(bookId);
    }

    @GetMapping("/name/{bookName}")
    public List<Book> findByName(@PathVariable String bookName) {
        return bookService.findByName(bookName);
    }

    @GetMapping("/author/{authorName}")
    public List<Book> findByAuthor(@PathVariable String authorName) {
        return bookService.findByAuthor(authorName);
    }

    @GetMapping("/genre/{genreName}")
    public Book findByGenre(@PathVariable String genreName) {
        return bookService.findByGenre(genreName);
    }

    @PutMapping("/{bookId}")
    public void update(@PathVariable Long bookId, @RequestBody Book book) {
        bookService.update(bookId, book);
    }

    @DeleteMapping("/{bookId}")
    public void delete(@PathVariable Long bookId) {
        bookService.delete(bookId);
    }
}
