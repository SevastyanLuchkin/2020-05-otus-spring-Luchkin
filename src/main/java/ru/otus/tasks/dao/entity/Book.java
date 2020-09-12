package ru.otus.tasks.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("book")
public class Book {

    @Id
    private long id;

    private String name;

    private List<String> authors;

    private List<String> genres;

    private boolean taken;
}
