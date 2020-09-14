package ru.otus.tasks.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.tasks.dao.entity.Author;

@Repository
public interface AuthorRepository extends MongoRepository<Author, String> {
}
