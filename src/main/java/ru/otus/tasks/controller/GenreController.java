package ru.otus.tasks.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.tasks.dao.entity.Genre;
import ru.otus.tasks.service.GenreService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genre")
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public Page<Genre> findAll(Pageable pageable) {
        return genreService.findAll(pageable);
    }
}
