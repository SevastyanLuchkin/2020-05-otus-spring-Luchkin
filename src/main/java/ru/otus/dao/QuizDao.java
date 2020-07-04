package ru.otus.dao;

import ru.otus.dao.entity.Quiz;

import java.util.List;
import java.util.Optional;

public interface QuizDao {

    Optional<Quiz> findById(long id);

    List<Quiz> findAll();
}
