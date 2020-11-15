package ru.otus.tasks.dao.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.otus.tasks.dao.entity.Genre;

@Repository
public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {

    Mono<Genre> findFirstByName(String name);
}
