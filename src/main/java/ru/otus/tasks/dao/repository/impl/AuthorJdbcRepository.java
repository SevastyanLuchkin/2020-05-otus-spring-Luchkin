package ru.otus.tasks.dao.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.tasks.dao.entity.Author;
import ru.otus.tasks.dao.repository.AuthorRepository;

@Repository
@RequiredArgsConstructor
public class AuthorJdbcRepository implements AuthorRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public void create(Author author, long bookFk) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("name", author.getName())
                .addValue("book_id", bookFk);
        jdbcOperations.update("insert into authors (name, book_id) values (:name, :book_id)", parameterSource);
    }
}
