package ru.otus.tasks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.otus.tasks.dao.entity.Genre;
import ru.otus.tasks.dao.repository.GenreRepository;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public Flux<Genre> findAll() {
        return genreRepository.findAll();
    }
}
