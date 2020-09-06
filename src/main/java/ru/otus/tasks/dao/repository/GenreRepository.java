package ru.otus.tasks.dao.repository;

import ru.otus.tasks.dao.entity.Author;
import ru.otus.tasks.dao.entity.Genre;

import java.util.List;

public interface GenreRepository {

    Genre findByName(String genreName);

    List<Author> findAll();
}
