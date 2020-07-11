package ru.otus.tasks.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Book {

    private long id;

    private String name;

    private Author author;

    private Genre genre;

    private boolean taken;
}
