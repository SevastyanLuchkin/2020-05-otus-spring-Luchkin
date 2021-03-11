package ru.otus.tasks.dao.entity;

import lombok.*;

import javax.persistence.*;

@Data
@ToString(exclude = "book")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "GENRES")
public class Genre {

    @Id
    @SequenceGenerator(name = "S_GENRE_GEN", sequenceName = "S_GENRE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_GENRE_GEN")
    @Column(name = "GENRE_ID")
    private long id;

    @Column(name = "NAME")
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "BOOK_ID")
    private Book book;
}
