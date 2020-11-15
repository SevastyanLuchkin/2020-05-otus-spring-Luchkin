package ru.otus.tasks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.otus.tasks.dao.entity.Author;
import ru.otus.tasks.dao.repository.AuthorRepository;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public Flux<Author> findAll() {
        return authorRepository.findAll();
    }
}
