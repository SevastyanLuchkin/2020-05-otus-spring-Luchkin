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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.otus.tasks.dao.repository.impl.TestData.EXPECTED;
import static ru.otus.tasks.dao.repository.impl.TestData.EXPECTED_LIST;

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
        assertBooks(EXPECTED, actual);
    }

    @Test
    void findByNameAndAuthorAndGenre() {
        Book actual = bookJpaRepository.findByNameAndAuthorAndGenre("book", "author", "genre");
        assertBooks(EXPECTED, actual);
    }

    @Test
    void findByAuthor() {
        List<Book> actual = bookJpaRepository.findByAuthor("author");
        assertBooks(actual.get(0), EXPECTED);
    }

    @Test
    void findByGenre() {
        List<Book> actual = bookJpaRepository.findByGenre("genre");
        assertBooks(actual.get(0), EXPECTED);
    }

    @Test
    void findByName() {
        List<Book> actual = bookJpaRepository.findByName("book");
        assertBooks(actual.get(0), EXPECTED);
    }

    @Test
    void findAll() {
        List<Book> actual = bookJpaRepository.findAll();
        assertEquals(actual.size(), EXPECTED_LIST.size());
        for (int i = 0; i < actual.size(); i++) {
            assertBooks(actual.get(i), EXPECTED_LIST.get(i));
        }
    }

    @Test
    void update() {
        Book origin = bookJpaRepository.findById(3);
        origin.setName("new");
        origin.getGenre().get(0).setName("new");
        origin.getAuthor().get(0).setName("new");
        bookJpaRepository.update(origin);
        Book actual = bookJpaRepository.findById(3);
        assertEquals(actual.getName(), "new");
        assertEquals(actual.getGenre().get(0).getName(), "new");
        assertEquals(actual.getAuthor().get(0).getName(), "new");
    }

    @Test
    void delete() {
        bookJpaRepository.delete(3);
        assertNull(bookJpaRepository.findById(3));
    }

    private void assertBooks(Book expected, Book actual) {
        assertEquals(expected.getName(), actual.getName());

        assertEquals(expected.getAuthor().size(), actual.getAuthor().size());
        List<Author> expectedAuthors = expected.getAuthor();
        List<Author> actualAuthors = actual.getAuthor();
        for (int i = 0; i < expectedAuthors.size(); i++) {
            assertEquals(expectedAuthors.get(i).getName(), actualAuthors.get(i).getName());
        }

        assertEquals(expected.getGenre().size(), actual.getGenre().size());
        List<Genre> expectedGenre = expected.getGenre();
        List<Genre> actualGenre = actual.getGenre();
        for (int i = 0; i < expectedGenre.size(); i++) {
            assertEquals(expectedGenre.get(i).getName(), actualGenre.get(i).getName());
        }
    }

    public static Book createBook(String bookName, List<Author> author, List<Genre> genre) {
        return Book.builder()
                .name(bookName)
                .author(author)
                .genre(genre)
                .build();
    }

    public static List<Genre> createGenres(String... genres) {
        return Stream.of(genres)
                .map(genre -> Genre.builder()
                        .name(genre)
                        .build())
                .collect(Collectors.toList());
    }

    public static List<Author> createAuthors(String... authors) {
        return Stream.of(authors)
                .map(author -> Author.builder()
                        .name(author)
                        .build())
                .collect(Collectors.toList());
    }
}