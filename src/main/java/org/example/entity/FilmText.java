package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Getter
@Setter
@Entity
@Table(name="film_text", schema = "movie")
public class FilmText {
    @Id
    @Column(name="film_id")
    private Short id;

    @OneToOne
    @JoinColumn(name="film_id")
    private Film film;

    @Column(name="title")
    private String title;

    @Column(name="description", columnDefinition = "text")
    @Type(type = "text")
    private String description;
}
