//package ru.otus.tasks.controller;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.context.annotation.Profile;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import ru.otus.tasks.dao.entity.Author;
//import ru.otus.tasks.dao.entity.Book;
//import ru.otus.tasks.dao.entity.Genre;
//
//import java.util.Map;
//import java.util.Set;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static ru.otus.tasks.controller.TestData.*;
//
//@Profile("IntegrationTest")
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class BookControllerTest {
//
//    @Autowired
//    private TestRestTemplate testRestTemplate;
//
//    @Test
//    void create() {
//        ResponseEntity<Book> bookResponseEntity = testRestTemplate.postForEntity("/book", NEW, Book.class);
//        assertEquals(bookResponseEntity.getStatusCode(), HttpStatus.CREATED);
//        Book bookResponse = bookResponseEntity.getBody();
//        assertBook(bookResponse, NEW);
//    }
//
//    @Test
//    void findAll() {
//        ResponseEntity<Book[]> booksResponse = testRestTemplate.getForEntity("/book", Book[].class);
//        assertEquals(booksResponse.getStatusCode(), HttpStatus.OK);
//        Book[] books = booksResponse.getBody();
//        assertNotNull(books);
//        assertTrue(books.length != 0);
//    }
//
//    @Test
//    void findById() {
//        ResponseEntity<Book> bookResponseEntity = testRestTemplate.getForEntity("/book/" + EXPECTED.getId(), Book.class);
//        assertEquals(bookResponseEntity.getStatusCode(), HttpStatus.OK);
//        Book bookResponse = bookResponseEntity.getBody();
//        assertBook(bookResponse, EXPECTED);
//    }
//
//    @Test
//    void findByName() {
//        ResponseEntity<Book[]> bookResponseEntity = testRestTemplate.getForEntity("/book/name/" + EXPECTED.getName(), Book[].class);
//        assertEquals(bookResponseEntity.getStatusCode(), HttpStatus.OK);
//        Book[] bookResponse = bookResponseEntity.getBody();
//        assertNotNull(bookResponse);
//        assertBook(bookResponse[0], EXPECTED);
//    }
//
//    @Test
//    @SuppressWarnings("OptionalGetWithoutIsPresent")
//    void findByAuthor() {
//        String authorName = EXPECTED.getAuthors().stream().findAny().get().getName();
//        ResponseEntity<Book[]> bookResponseEntity = testRestTemplate.getForEntity("/book/author/" + authorName, Book[].class);
//        assertEquals(bookResponseEntity.getStatusCode(), HttpStatus.OK);
//        Book[] bookResponse = bookResponseEntity.getBody();
//        assertNotNull(bookResponse);
//        assertBook(bookResponse[0], EXPECTED);
//    }
//
//    @Test
//    @SuppressWarnings("OptionalGetWithoutIsPresent")
//    void findByGenre() {
//        String genreName = EXPECTED.getGenres().stream().findAny().get().getName();
//        ResponseEntity<Book> bookResponseEntity = testRestTemplate.getForEntity("/book/genre/" + genreName, Book.class);
//        assertEquals(bookResponseEntity.getStatusCode(), HttpStatus.OK);
//        Book bookResponse = bookResponseEntity.getBody();
//        assertBook(bookResponse, EXPECTED);
//    }
//
//    @Test
//    void update() {
//        Book expectedBook = testRestTemplate.getForObject("/book/" + EXPECTED_ID, Book.class);
//        expectedBook.setTaken(true);
//        ResponseEntity<Void> exchange = testRestTemplate.exchange("/book/" + expectedBook.getId(),
//                HttpMethod.PUT,
//                new HttpEntity<>(expectedBook),
//                Void.class);
//        ResponseEntity<Book> bookResponseEntity = testRestTemplate.getForEntity("/book/" + EXPECTED.getId(), Book.class);
//        assertEquals(bookResponseEntity.getStatusCode(), HttpStatus.OK);
//        Book bookResponse = bookResponseEntity.getBody();
//        assertBook(bookResponse, EXPECTED);
//    }
//
//    @Test
//    void delete() {
//    }
//
//    private void assertBook(Book actual, Book expected) {
//        assertNotNull(actual);
//        assertEquals(actual.getName(), expected.getName());
//        assertAuthors(actual.getAuthors(), expected.getAuthors());
//        assertGenres(actual.getGenres(), expected.getGenres());
//    }
//
//    private void assertGenres(Set<Genre> actual, Set<Genre> expected) {
//        assertEquals(actual.size(), expected.size());
//        Map<String, Genre> actualByName = groupGenresByName(actual);
//        Map<String, Genre> expectedByName = groupGenresByName(expected);
//        actualByName.forEach((k, v) -> assertEquals(v.getName(), expectedByName.get(k).getName()));
//    }
//
//    private void assertAuthors(Set<Author> actual, Set<Author> expected) {
//        assertEquals(actual.size(), expected.size());
//        Map<String, Author> actualByName = groupAuthorsByName(actual);
//        Map<String, Author> expectedByName = groupAuthorsByName(expected);
//        actualByName.forEach((k, v) -> assertEquals(v.getName(), expectedByName.get(k).getName()));
//    }
//
//    private Map<String, Genre> groupGenresByName(Set<Genre> actual) {
//        return actual.stream()
//                .collect(Collectors.toMap(Genre::getName, Function.identity()));
//    }
//
//    private Map<String, Author> groupAuthorsByName(Set<Author> actual) {
//        return actual.stream()
//                .collect(Collectors.toMap(Author::getName, Function.identity()));
//    }
//}