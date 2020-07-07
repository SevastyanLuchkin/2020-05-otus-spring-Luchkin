package ru.otus.service.io.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.otus.service.io.IOService;

import java.io.BufferedReader;
import java.io.PrintStream;

@RequiredArgsConstructor
public class IOServiceImpl implements IOService {

    private final BufferedReader inputStream;

    private final PrintStream outStream;

    @Override
    @SneakyThrows
    public String in() {
        return inputStream.readLine();
    }

    @Override
    public void out(String message) {
        outStream.println(message);
    }
}
