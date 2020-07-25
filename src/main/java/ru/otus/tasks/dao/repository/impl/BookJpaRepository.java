package ru.otus.tasks.dao.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.tasks.dao.entity.Book;
import ru.otus.tasks.dao.repository.BookRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookJpaRepository implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public long create(Book book) {
        em.persist(book);
        return book.getId();
    }

    @Override
    public Book findById(long id) {
        return em.find(Book.class, id);
    }

    @Override
    public Book findByNameAndAuthorAndGenre(String bookName, String authorName, String genreName) {
        List<Book> books = em.createQuery("select b from Book b join fetch b.author a " +
                "where b.name = :bookName and a.name = :authorName", Book.class)
                .setParameter("bookName", bookName)
                .setParameter("authorName", authorName)
                .getResultList();
        return em.createQuery("select b from Book b join fetch b.genre g where b in (:books) and g.name = :genreName", Book.class)
                .setParameter("books", books)
                .setParameter("genreName", genreName)
                .getSingleResult();
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return em.createQuery("select b from Book b join fetch b.author a where a.name =: author", Book.class)
                .setParameter("author", author)
                .getResultList();
    }

    @Override
    public List<Book> findByGenre(String genre) {
        return em.createQuery("select b from Book b join fetch b.genre g where g.name = : genre", Book.class)
                .setParameter("genre", genre)
                .getResultList();
    }

    @Override
    public List<Book> findByName(String name) {
        return em.createQuery("select b from Book b where b.name = :name", Book.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = em.createQuery("select b from Book b join fetch b.author", Book.class)
                .getResultList();
        return em.createQuery("select b from Book b join fetch b.genre where b in :books", Book.class)
                .setParameter("books", books)
                .getResultList();
    }

    @Override
    public void update(Book book) {
        if (book.getId() == 0) {
            create(book);
        } else {
            em.merge(book);
        }
    }

    @Override
    public void delete(long id) {
        Book persistedBook = em.find(Book.class, id);
        em.remove(persistedBook);
    }
}
