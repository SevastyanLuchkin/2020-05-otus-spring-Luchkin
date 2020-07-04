package ru.otus.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.config.QuizProperties;
import ru.otus.dao.QuizDao;
import ru.otus.dao.entity.Quiz;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class QuizDaoCsv implements QuizDao {

    private final QuizProperties quizProperties;

    @Override
    public Optional<Quiz> findById(long id) {
        try (Stream<String> lines = Files.lines(Paths.get((quizProperties.getSource())))) {
            return lines.map(line -> line.split(Pattern.quote(quizProperties.getDelimiter())))
                    .filter(line -> Long.parseLong(line[quizProperties.getIdIndex()]) == id)
                    .map(line -> Quiz.builder()
                            .id(Long.parseLong(line[quizProperties.getIdIndex()]))
                            .answer(line[quizProperties.getAnswerIndex()])
                            .question(line[quizProperties.getQuestionIndex()])
                            .build())
                    .findAny();
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Quiz> findAll() {
        try (Stream<String> lines = Files.lines(Paths.get(quizProperties.getSource()))) {
            return lines.map(line -> line.split(Pattern.quote(quizProperties.getDelimiter())))
                    .map(line -> Quiz.builder()
                            .id(Long.parseLong(line[quizProperties.getIdIndex()]))
                            .answer(line[quizProperties.getAnswerIndex()])
                            .question(line[quizProperties.getQuestionIndex()])
                            .build())
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            return Collections.emptyList();
        }
    }
}
