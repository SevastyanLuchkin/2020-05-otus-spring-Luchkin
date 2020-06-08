package ru.otus.spring.service.csv;

import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ResourceReaderCsv implements ResourceReader {

    private final String QUESTIONS_PATH;

    @Override
    public String read() {
        InputStream resource = this.getClass().getResourceAsStream(QUESTIONS_PATH);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Проблемы с чтением файла");
        }
    }
}
