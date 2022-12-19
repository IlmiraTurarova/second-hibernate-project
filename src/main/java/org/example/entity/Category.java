package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="category", schema = "movie")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Byte categoryId;

    @Column(name="name")
    private String name;

    @Column(name="last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    @ManyToMany
    @JoinTable(name="film_category",
            joinColumns = @JoinColumn(name="category_id", referencedColumnName = "category_id"),
            inverseJoinColumns = @JoinColumn(name="film_id", referencedColumnName = "film_id"))
    private Set<Film> films;
}
