package ru.otus.tasks.dao.repository;

import ru.otus.tasks.dao.entity.Author;

import java.util.List;

public interface AuthorRepository {

    List<Author> findByName(String name);
}
