package ru.otus.tasks.dao.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.otus.tasks.dao.entity.Author;

@Repository
public interface AuthorRepository extends ReactiveCrudRepository<Author, String> {

    Mono<Author> findFirstByName(String name);
}
