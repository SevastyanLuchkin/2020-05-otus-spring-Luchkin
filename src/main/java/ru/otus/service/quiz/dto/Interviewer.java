package ru.otus.service.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Interviewer {

    private String name;

    private boolean success;
}
