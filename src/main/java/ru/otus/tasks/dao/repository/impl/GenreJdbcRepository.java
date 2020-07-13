package ru.otus.tasks.dao.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.tasks.dao.entity.Genre;
import ru.otus.tasks.dao.repository.GenreRepository;

@Repository
@RequiredArgsConstructor
public class GenreJdbcRepository implements GenreRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public void create(Genre genre, long bookId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", genre.getName())
                .addValue("book_id", bookId);
        jdbcOperations.update("insert into genres (name, book_id) values (:name, :book_id)", parameterSource);
    }
}
