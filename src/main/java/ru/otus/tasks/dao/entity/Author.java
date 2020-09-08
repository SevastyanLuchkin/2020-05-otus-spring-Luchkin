package ru.otus.tasks.dao.entity;

import lombok.*;

import javax.persistence.*;

@Data
@ToString(exclude = "book")
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

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "BOOK_ID")
    private Book book;
}
