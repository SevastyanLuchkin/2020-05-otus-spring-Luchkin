package ru.otus.tasks.dao.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.tasks.dao.entity.Author;
import ru.otus.tasks.dao.repository.AuthorRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuthorJpaRepository implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Author findByName(String name) {
        return em.createQuery("select a from Author a " +
                "where a.name =:name", Author.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public List<Author> findAll() {
        return em.createQuery("select a from Author a").getResultList();
    }
}
