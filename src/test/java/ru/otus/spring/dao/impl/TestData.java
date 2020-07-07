package ru.otus.spring.dao.impl;


import ru.otus.dao.entity.Quiz;

import java.util.Arrays;
import java.util.List;

public class TestData {

    public static final Quiz EXPECTED = Quiz.builder()
            .id(1)
            .question("What’s your favorite color?")
            .answer("darcula")
            .build();

    public static final List<Quiz> EXPECTED_LIST = Arrays.asList(EXPECTED,
            Quiz.builder()
                    .id(2)
                    .question("What makes you happiest?")
                    .answer("java")
                    .build(),
            Quiz.builder()
                    .id(3)
                    .question("When is the last time you can remember feeling totally at peace?")
                    .answer("after defended pull request")
                    .build(),
            Quiz.builder()
                    .id(4)
                    .question("Which foreign language would you like to learn?")
                    .answer("scala")
                    .build(),
            Quiz.builder()
                    .id(5)
                    .question("What’s your favorite joke?")
                    .answer("javascript is programming language")
                    .build());
}
