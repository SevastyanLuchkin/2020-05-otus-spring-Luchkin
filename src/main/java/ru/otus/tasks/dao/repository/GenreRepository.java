package ru.otus.tasks.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.tasks.dao.entity.Genre;

@Repository
public interface GenreRepository extends MongoRepository<Genre, String> {
}
