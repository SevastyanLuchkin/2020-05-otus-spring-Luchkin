package ru.otus.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Quiz {

    private long id;

    private String answer;

    private String question;
}
