package ru.otus.tasks.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Builder
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

    @OneToMany
    @JoinColumn(name = "BOOK_ID")
    private List<Author> author;

    @OneToMany
    @JoinColumn(name = "BOOK_ID")
    private List<Genre> genre;

    @Column(name = "TAKEN")
    private boolean taken;
}
