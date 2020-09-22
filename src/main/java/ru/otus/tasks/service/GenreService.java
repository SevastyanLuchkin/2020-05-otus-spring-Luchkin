package ru.otus.tasks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.otus.tasks.dao.entity.Genre;
import ru.otus.tasks.dao.repository.GenreRepository;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public Page<Genre> findAll(Pageable pageable) {
        return genreRepository.findAll(pageable);
    }
}
