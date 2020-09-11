package ru.otus.tasks.dao.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BOOKS")
public class Book {

    @Id
    @SequenceGenerator(name = "S_BOOK_GEN", sequenceName = "S_BOOK", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_BOOK_GEN")
    @Column(name = "BOOK_ID")
    private long id;

    @Column(name = "NAME")
    private String name;

    @ManyToMany(mappedBy = "books", cascade = CascadeType.ALL)
    private Set<Author> authors;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<Genre> genres;

    @Column(name = "TAKEN")
    private boolean taken;
}
