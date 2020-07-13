package ru.otus.tasks.dao.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.tasks.dao.entity.Author;
import ru.otus.tasks.dao.entity.Book;
import ru.otus.tasks.dao.entity.Genre;
import ru.otus.tasks.dao.repository.BookRepository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookJdbcRepository implements BookRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public long create(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("name", book.getName())
                .addValue("taken", book.isTaken());
        jdbcOperations.update("insert into books (name, taken) values (:name, :taken)", parameterSource, keyHolder);
        return (long) keyHolder.getKey();
    }

    @Override
    public Book findById(long id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("id", id);
        return jdbcOperations.queryForObject("select * from books where book_id = :id",
                parameterSource, (rs, rowNum) -> bookRowMapper(rs));
    }

    @Override
    public Book findByNameAndAuthorAndGenre(String book, String author, String genre) {
        String query = "select books.book_id, " +
                "books.name as book_name, " +
                "books.taken, " +
                "authors.author_id, " +
                "authors.name as author_name, " +
                "genres.genre_id, " +
                "genres.name as genre_name " +
                "from books books join authors authors on books.book_id = authors.book_id " +
                "join genres genres on books.book_id = genres.book_id " +
                "where books.name = :book and authors.name = :author and genres.name = :genre";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("book", book)
                .addValue("author", author)
                .addValue("genre", genre);
        return jdbcOperations.queryForObject(query, parameterSource, (rs, rowNum) -> fullBookRowMapper(rs));
    }

    @Override
    public List<Book> findByAuthor(String author) {
        String query = "select books.book_id, " +
                "books.name as book_name, " +
                "books.taken, " +
                "authors.author_id, " +
                "authors.name as author_name, " +
                "from books books join authors authors on books.book_id = authors.book_id " +
                "where authors.name = :author";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("author", author);
        return jdbcOperations.query(query, parameterSource, (rs, rowNum) -> fullBookRowMapper(rs));
    }

    @Override
    public List<Book> findByGenre(String genre) {
        String query = "select books.book_id, " +
                "books.name as book_name, " +
                "books.taken, " +
                "genres.genre_id, " +
                "genres.name as genre_name " +
                "from books books join genres genres on books.book_id = genres.book_id " +
                "where genres.name = :genre";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("genre", genre);
        return jdbcOperations.query(query, parameterSource, (rs, rowNum) -> fullBookRowMapper(rs));
    }

    @Override
    public List<Book> findByName(String name) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("name", name);
        return jdbcOperations.query("select * from books b where b.name = :name",
                parameterSource, (rs, rowNum) -> bookRowMapper(rs));
    }

    @Override
    public List<Book> findAll() {
        return jdbcOperations.query("select * from books b", (rs, rowNum) -> bookRowMapper(rs));
    }

    @Override
    public void update(Book book) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("name", book.getName())
                .addValue("taken", book.isTaken())
                .addValue("id", book.getId());
        jdbcOperations.update("update books set name = :name, taken =:taken where book_id = :id", parameterSource);
    }

    @Override
    public void delete(long id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("id", id);
        jdbcOperations.update("delete from books where book_id = :id", parameterSource);
    }

    /**
     * Маппер только для книги
     *
     * @param rs ResultSet
     * @return {@link Book}
     */
    private Book bookRowMapper(ResultSet rs) throws SQLException {
        return Book.builder()
                .id(rs.getLong("book_id"))
                .name(rs.getString("name"))
                .taken(rs.getBoolean("taken"))
                .build();
    }

    /**
     * Маппер, включая вложенные сущности
     *
     * @param rs ResultSet
     * @return {@link Book}
     */
    private Book fullBookRowMapper(ResultSet rs) throws SQLException {
        return Book.builder()
                .id(rs.getLong("book_id"))
                .name(rs.getString("book_name"))
                .taken(rs.getBoolean("taken"))
                .author(mapAuthor(rs))
                .genre(mapGenre(rs))
                .build();
    }

    private Genre mapGenre(ResultSet rs) throws SQLException {
        return !isResultSetContainsColumn(rs, "genre_id") ? null :
                Genre.builder()
                        .id(rs.getLong("genre_id"))
                        .name(rs.getString("genre_name"))
                        .build();
    }

    private Author mapAuthor(ResultSet rs) throws SQLException {
        return !isResultSetContainsColumn(rs, "author_id") ? null :
                Author.builder()
                        .id(rs.getLong("author_id"))
                        .name(rs.getString("author_name"))
                        .build();
    }

    private boolean isResultSetContainsColumn(ResultSet rs, String columnName) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            if (metaData.getColumnName(i).equalsIgnoreCase(columnName)) {
                return true;
            }
        }
        return false;
    }
}
