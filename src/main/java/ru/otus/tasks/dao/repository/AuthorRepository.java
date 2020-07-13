package ru.otus.tasks.dao.repository;

import ru.otus.tasks.dao.entity.Author;

public interface AuthorRepository {

    void create(Author author, long bookFk);
}
