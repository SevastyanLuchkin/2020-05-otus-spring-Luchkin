package ru.otus.tasks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.tasks.dao.entity.Book;
import ru.otus.tasks.dao.repository.AuthorRepository;
import ru.otus.tasks.dao.repository.BookRepository;
import ru.otus.tasks.dao.repository.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    public Book create(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public void update(String id, Book book) {
        Book originBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Книга не найдена"));
        originBook.setName(book.getName());
        originBook.setTaken(book.isTaken());
    }

    public Book findById(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Автор не найден"));
    }

    public List<Book> findByName(String name) {
        return bookRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public List<Book> findAll(Pageable pageable) {
        Page<Book> books = bookRepository.findAll(pageable);
        return books.getContent();
    }

    public void delete(String id) {
        bookRepository.deleteById(id);
    }
}
