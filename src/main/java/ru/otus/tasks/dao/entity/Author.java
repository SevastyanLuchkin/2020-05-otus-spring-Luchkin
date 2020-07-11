package ru.otus.tasks.dao.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Author {

    private long id;

    private String name;
}