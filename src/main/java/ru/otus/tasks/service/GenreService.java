package ru.otus.tasks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.otus.tasks.dao.entity.Genre;
import ru.otus.tasks.dao.repository.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public List<Genre> findAll(Pageable pageable) {
        return genreRepository.findAll(pageable).getContent();
    }
}
