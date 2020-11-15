package ru.otus.tasks.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.tasks.dao.entity.Book;
import ru.otus.tasks.service.BookService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public Mono<Book> create(@RequestBody Book book) {
        return bookService.create(book);
    }

    @GetMapping
    public Flux<Book> findAll() {
        return bookService.findAll();
    }

    @GetMapping("/{bookId}")
    public Mono<Book> findById(@PathVariable String bookId) {
        return bookService.findById(bookId);
    }

    @GetMapping("/name/{bookName}")
    public Flux<Book> findByName(@PathVariable String bookName) {
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
