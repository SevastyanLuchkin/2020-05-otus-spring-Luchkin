package ru.otus.tasks.dao.repository;

import ru.otus.tasks.dao.entity.Genre;

public interface GenreRepository {

    void create(Genre genre, long bookId);
}
