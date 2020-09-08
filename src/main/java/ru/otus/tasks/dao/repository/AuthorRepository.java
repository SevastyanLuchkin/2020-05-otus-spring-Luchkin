package ru.otus.tasks.dao.repository;

import ru.otus.tasks.dao.entity.Author;

import java.util.List;

public interface AuthorRepository {

    Author findByName(String name);

    List<Author> findAll();
}
