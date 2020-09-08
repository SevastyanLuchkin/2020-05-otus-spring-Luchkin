//package ru.otus.tasks.dao.repository.impl;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//import ru.otus.tasks.dao.entity.Book;
//import ru.otus.tasks.dao.repository.BookRepository;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.util.List;
//
//@Repository
//@RequiredArgsConstructor
//public class BookJpaRepository implements BookRepository {
//
//    @PersistenceContext
//    private final EntityManager em;
//
//    @Override
//    @Transactional
//    public long create(Book book) {
//        em.persist(book);
//        return book.getId();
//    }
//
//    @Override
//    public Book findById(long id) {
//        return em.find(Book.class, id);
//    }
//
//    @Override
//    public Book findByNameAndAuthorAndGenre(String bookName, String authorName, String genreName) {
//        return em.createQuery("select b from Book b " +
//                "join fetch b.genres g " +
//                "join b.authors a " +
//                "where b.name = :bookName and a.name = :authorName and g.name = :genreName", Book.class)
//                .setParameter("bookName", bookName)
//                .setParameter("authorName", authorName)
//                .setParameter("genreName", genreName)
//                .getSingleResult();
//    }
//
//    @Override
//    public List<Book> findByName(String name) {
//        return em.createQuery("select b from Book b where b.name = :name", Book.class)
//                .setParameter("name", name)
//                .getResultList();
//    }
//
//    @Override
//    public List<Book> findAll() {
//        return em.createQuery("select b from Book b", Book.class)
//                .getResultList();
//    }
//
//    @Override
//    public void update(Book book) {
//        if (book.getId() == 0) {
//            create(book);
//        } else {
//            em.merge(book);
//        }
//    }
//
//    @Override
//    public void delete(long id) {
//        Book persistedBook = em.find(Book.class, id);
//        em.remove(persistedBook);
//    }
//}
