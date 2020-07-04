package ru.otus.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Data
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "quiz")
public class QuizProperties {

    private final String source;

    private final int idIndex;

    private final int questionIndex;

    private final int answerIndex;

    private final String delimiter;
}
