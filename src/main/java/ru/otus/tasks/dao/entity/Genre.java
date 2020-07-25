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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "GENRES")
public class Genre {

    @Id
    @SequenceGenerator(name = "S_GENRE_GEN", sequenceName = "S_GEN", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_GENRE_GEN")
    @Column(name = "GENRE_ID")
    private long id;

    @Column(name = "NAME")
    private String name;
}
