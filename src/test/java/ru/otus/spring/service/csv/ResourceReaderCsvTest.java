package ru.otus.spring.service.csv;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResourceReaderCsvTest {

    @Test
    void read() throws NoSuchFieldException, IllegalAccessException {
        String sb = "darcula" + '\n' +
                "java" + '\n' +
                "after defended pull request" + '\n' +
                "scala" + '\n' +
                "javascript is programming language" + '\n';

        InputStream is = new ByteArrayInputStream(sb.getBytes());
        System.setIn(is);

        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bs);
        System.setOut(ps);

        ResourceReaderCsv readerCsv = new ResourceReaderCsv();

        Field questionField = readerCsv.getClass().getDeclaredField("questionsPath");
        questionField.setAccessible(true);
        questionField.set(readerCsv, "/questions.csv");

        Field pathField = readerCsv.getClass().getDeclaredField("answersPath");
        pathField.setAccessible(true);
        pathField.set(readerCsv, "/answers.csv");

        readerCsv.read();
        String[] output = bs.toString().split(Pattern.quote("\n"));
        assertEquals("Тест пройден", output[output.length - 1]);
    }
}
