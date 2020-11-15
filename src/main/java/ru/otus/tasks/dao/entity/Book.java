package ru.otus.tasks.dao.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("books")
public class Book {

    @Id
    private String id;

    private String name;

    private List<String> authors;

    private List<String> genres;

    private boolean taken;
}
