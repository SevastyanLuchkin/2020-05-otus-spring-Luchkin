package ru.otus.spring.service.csv;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResourceReaderCsvTest {

    private final String PATH = "/questions.csv";

    @Test
    void read() throws IOException {
        ResourceReaderCsv readerCsv = new ResourceReaderCsv(PATH);
        String read = readerCsv.read();

        InputStream resource = this.getClass().getResourceAsStream(PATH);
        String lines;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource))) {
            lines = reader.lines().collect(Collectors.joining("\n"));
        }

        assertEquals(lines, read);
    }
}