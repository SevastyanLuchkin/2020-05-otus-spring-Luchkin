package ru.otus.tasks.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.tasks.dao.entity.Genre;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    @Query("select g from Genre g join fetch g.book b join fetch b.authors join fetch b.genres where g.name = :genreName")
    Optional<Genre> findByName(String genreName);
}
