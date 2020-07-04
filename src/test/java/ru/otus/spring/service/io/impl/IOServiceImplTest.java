package ru.otus.spring.service.io.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.service.io.IOService;
import ru.otus.service.io.impl.IOServiceImpl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IOServiceImplTest {

    private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("Hello Spring".getBytes())));
    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private PrintStream printStream = new PrintStream(byteArrayOutputStream);
    private IOService ioService = new IOServiceImpl(bufferedReader, printStream);

    private PrintStream systemOut;

    @BeforeEach
    void setUp() {
        PrintStream systemOut = System.out;
        System.setOut(printStream);
    }

    @AfterEach
    void tearDown() {
        System.setOut(systemOut);
    }

    @Test
    void in() {
        assertEquals("Hello Spring", ioService.in());
    }

    @Test
    void out() {
        ioService.out("Hello Spring");
        assertEquals("Hello Spring" + "\n", byteArrayOutputStream.toString());
    }
}
