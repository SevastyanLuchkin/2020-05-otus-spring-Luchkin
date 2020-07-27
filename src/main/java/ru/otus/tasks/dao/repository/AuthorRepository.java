package ru.otus.tasks.dao.repository;

import ru.otus.tasks.dao.entity.Author;

import java.util.List;
import java.util.Map;

public interface AuthorRepository {

    Map<Long, List<Author>> findByName(String name);
}
