package ru.otus.tasks.dao.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = "books")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "AUTHORS")
public class Author {

    @Id
    @SequenceGenerator(name = "S_AUTHOR_GEN", sequenceName = "S_AUTHOR", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_AUTHOR_GEN")
    @Column(name = "AUTHOR_ID")
    private long id;

    @Column(name = "NAME")
    private String name;

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "AUTHOR_BOOK",
            joinColumns = @JoinColumn(name = "AUTHOR_ID"),
            inverseJoinColumns = @JoinColumn(name = "BOOK_ID"))
    private List<Book> books = new ArrayList<>();
}
