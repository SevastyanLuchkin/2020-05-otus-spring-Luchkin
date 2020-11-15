package ru.otus.tasks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.tasks.dao.entity.Book;
import ru.otus.tasks.dao.repository.BookRepository;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private final ReactiveMongoOperations mongoOperations;

    public Mono<Book> create(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public void update(String id, Book book) {
        bookRepository.findById(id)
                .map(originBook -> {
                    originBook.setTaken(book.isTaken());
                    originBook.setName(book.getName());
                    originBook.setAuthors(book.getAuthors());
                    originBook.setGenres(book.getGenres());
                    return originBook;
                });
    }

    public Mono<Book> findById(String id) {
        return bookRepository.findById(id);
    }

    public Flux<Book> findByName(String name) {
        return bookRepository.findByName(name);
    }

    public Flux<Book> findAll() {
        return bookRepository.findAll();
    }

    public void delete(String id) {
        bookRepository.deleteById(id);
    }
}
