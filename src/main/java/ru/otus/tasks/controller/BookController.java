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
    public Book findById(@PathVariable String bookId) {
        return bookService.findById(bookId);
    }

    @GetMapping("/name/{bookName}")
    public List<Book> findByName(@PathVariable String bookName) {
        return bookService.findByName(bookName);
    }

    @PutMapping("/{bookId}")
    public void update(@PathVariable String bookId, @RequestBody Book book) {
        bookService.update(bookId, book);
    }

    @DeleteMapping("/{bookId}")
    public void delete(@PathVariable String bookId) {
        bookService.delete(bookId);
    }
}
