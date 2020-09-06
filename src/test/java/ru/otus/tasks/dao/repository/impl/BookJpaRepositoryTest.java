package ru.otus.tasks.dao.repository.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.tasks.dao.entity.Author;
import ru.otus.tasks.dao.entity.Book;
import ru.otus.tasks.dao.entity.Genre;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.otus.tasks.dao.repository.impl.TestData.EXPECTED_BOOK;
import static ru.otus.tasks.dao.repository.impl.TestData.EXPECTED_BOOKS;

@DataJpaTest
@Import(BookJpaRepository.class)
class BookJpaRepositoryTest {

    @Autowired
    TestEntityManager tem;

    @Autowired
    BookJpaRepository bookJpaRepository;

    @Test
    void create() {
        Book expected = createBook("b", createAuthors("a"), createGenres("g"));
        long id = bookJpaRepository.create(expected);
        Book actual = tem.find(Book.class, id);
        assertBooks(expected, actual);
    }

    @Test
    void findById() {
        Book actual = bookJpaRepository.findById(3L);
        assertBooks(EXPECTED_BOOK, actual);
    }

    @Test
    void findByNameAndAuthorAndGenre() {
        Book actual = bookJpaRepository.findByNameAndAuthorAndGenre("book", "author", "genre");
        assertBooks(EXPECTED_BOOK, actual);
    }

    @Test
    void findByName() {
        List<Book> actual = bookJpaRepository.findByName("book");
        assertBooks(actual.get(0), EXPECTED_BOOK);
    }

    @Test
    void findAll() {
        List<Book> actual = bookJpaRepository.findAll();
        assertEquals(actual.size(), EXPECTED_BOOKS.size());
        for (int i = 0; i < actual.size(); i++) {
            assertBooks(actual.get(i), EXPECTED_BOOKS.get(i));
        }
    }

    @Test
    void update() {
        Book origin = bookJpaRepository.findById(3);
        origin.setName("new");
        origin.getGenres().stream().findAny().get().setName("new");
        origin.getAuthors().stream().findAny().get().setName("new");
        bookJpaRepository.update(origin);
        Book actual = bookJpaRepository.findById(3);
        assertEquals(actual.getName(), "new");
        assertEquals(actual.getGenres().stream().findAny().get().getName(), "new");
        assertEquals(actual.getAuthors().stream().findAny().get().getName(), "new");
    }

    @Test
    void delete() {
        bookJpaRepository.delete(3);
        assertNull(bookJpaRepository.findById(3));
    }

    private void assertBooks(Book expected, Book actual) {
        assertEquals(expected.getName(), actual.getName());

        assertEquals(expected.getAuthors().size(), actual.getAuthors().size());
        Set<Author> expectedAuthors = expected.getAuthors();
        Set<Author> actualAuthors = actual.getAuthors();
        for (int i = 0; i < expectedAuthors.size(); i++) {
            assertEquals(expectedAuthors.stream().findAny().get().getName(), actualAuthors.stream().findAny().get().getName());
        }

        assertEquals(expected.getGenres().size(), actual.getGenres().size());
        Set<Genre> expectedGenre = expected.getGenres();
        Set<Genre> actualGenre = actual.getGenres();
        for (int i = 0; i < expectedGenre.size(); i++) {
            assertEquals(expectedGenre.stream().findAny().get().getName(), actualGenre.stream().findAny().get().getName());
        }
    }

    public static Book createBook(String bookName, Set<Author> author, Set<Genre> genre) {
        return Book.builder()
                .name(bookName)
                .authors(author)
                .genres(genre)
                .build();
    }

    public static Set<Genre> createGenres(String... genres) {
        return Stream.of(genres)
                .map(genre -> Genre.builder()
                        .name(genre)
                        .build())
                .collect(Collectors.toSet());
    }

    public static Set<Author> createAuthors(String... authors) {
        return Stream.of(authors)
                .map(author -> Author.builder()
                        .name(author)
                        .build())
                .collect(Collectors.toSet());
    }
}