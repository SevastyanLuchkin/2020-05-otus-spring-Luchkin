package ru.otus.tasks.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.tasks.dao.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, Long> {

    List<Book> findByName(String name);

    @Query(value = "{ 'authors' : ?0 }")
    List<Book> findByAuthorName(String name);

    @Query(value = "{ 'genres' : ?0 }")
    List<Book> findByGenreName(String name);
}
