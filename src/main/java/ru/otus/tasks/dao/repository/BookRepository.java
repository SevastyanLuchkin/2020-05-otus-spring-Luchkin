package ru.otus.tasks.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.tasks.dao.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findByName(String name);

    @Query(value = "{ 'authors.id' : ?0 }")
    List<Book> findByAuthorId(String id);

    @Query(value = "{ 'genres.id' : ?0 }")
    List<Book> findByGenreId(String id);
}
