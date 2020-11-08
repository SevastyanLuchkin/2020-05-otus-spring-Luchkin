package ru.otus.tasks.dao.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("genres")
public class Genre {

    @Id
    private String id;

    private String name;

    private Book book;
}
