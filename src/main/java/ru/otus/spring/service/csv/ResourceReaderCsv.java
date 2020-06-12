package ru.otus.spring.service.csv;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
@PropertySource("classpath:application.properties")
public class ResourceReaderCsv implements ResourceReader {

    @Value("${question.path}")
    private String questionsPath;

    @Value("${answer.path}")
    private String answersPath;

    @Value("${point.required}")
    private int requiredPoints;

    @Override
    public void read() {
        InputStream resourceQuestions = this.getClass().getResourceAsStream(questionsPath);
        InputStream resourceAnswers = this.getClass().getResourceAsStream(answersPath);
        try (BufferedReader readerQuestions = new BufferedReader(new InputStreamReader(resourceQuestions));
             BufferedReader readerAnswers = new BufferedReader(new InputStreamReader(resourceAnswers));
             BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in))) {
            String question;
            int rightAnswerCount = 0;
            while ((question = readerQuestions.readLine()) != null) {
                System.out.println(question);
                String userAnswer = inputReader.readLine().toLowerCase();
                String rightAnswer = readerAnswers.readLine();
                rightAnswerCount = userAnswer.equals(rightAnswer) ? ++rightAnswerCount : rightAnswerCount;
            }
            System.out.println(rightAnswerCount > requiredPoints ? "Тест пройден" : "Тест не пройден");
        } catch (IOException e) {
            throw new RuntimeException("Проблемы с чтением файла");
        }
    }
}
