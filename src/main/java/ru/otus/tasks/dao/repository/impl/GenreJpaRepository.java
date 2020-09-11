//package ru.otus.tasks.dao.repository.impl;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//import ru.otus.tasks.dao.entity.Author;
//import ru.otus.tasks.dao.entity.Genre;
//import ru.otus.tasks.dao.repository.GenreRepository;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.util.List;
//
//@Repository
//@RequiredArgsConstructor
//public class GenreJpaRepository implements GenreRepository {
//
//    @PersistenceContext
//    private final EntityManager em;
//
//    @Override
//    public Genre findByName(String name) {
//        return em.createQuery("select g from Genre g join g.book " +
//                "where g.name = :name", Genre.class)
//                .setParameter("name", name)
//                .getSingleResult();
//    }
//
//    @Override
//    public List<Author> findAll() {
//        return em.createQuery("select g from Genre g").getResultList();
//    }
//}
