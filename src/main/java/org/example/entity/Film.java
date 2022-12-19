package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Getter
@Setter
@Entity
@Table(name="film", schema = "movie")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="film_id")
    private Short id;

    @Column(name="title", length = 128)
    private String title;


    @Column(name="description", columnDefinition = "text")
    @Type(type = "text")
    private String description;

    @Column(name="release_year", columnDefinition = "year")
    @Convert(converter = YearConverter.class)
    private Year releaseYear;

    @ManyToOne
    @JoinColumn(name="language_id")
    private Language language;

    @ManyToOne
    @JoinColumn(name="original_language_id")
    private Language originalLanguage;

    @Column(name="rental_duration")
    private Byte rentalDuration;

    @Column(name="rental_rate")
    private BigDecimal rentalRate;

    @Column(name="length")
    private Short length;

    @Column(name="replacement_cost")
    private BigDecimal replacementCost;

    @Column(name="rating", columnDefinition = "enum('G', 'PG', 'PG-13', 'R', 'NC-17')")
    @Convert(converter = RatingConverter.class)
    private Rating rating;

    @Column(name="special_features", columnDefinition = "set('Trailers', 'Commentaries', 'Deleted Scenes', 'Behind the Scenes')")
    private String specialFeatures;

    @Column(name="last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    @ManyToMany
    @JoinTable(name="film_actor",
    joinColumns = @JoinColumn(name="film_id", referencedColumnName = "film_id"),
    inverseJoinColumns = @JoinColumn(name="actor_id", referencedColumnName = "actor_id"))
    private Set<Actor> actors;

    @ManyToMany
    @JoinTable(name="film_category",
            joinColumns = @JoinColumn(name="film_id", referencedColumnName = "film_id"),
            inverseJoinColumns = @JoinColumn(name="category_id", referencedColumnName = "category_id"))
    private Set<Category> categories;

    public Set<Feature> getSpecialFeatures() {
        if (isNull(specialFeatures) || getSpecialFeatures().isEmpty()) {
            return null;
        }

        Set<Feature> result = new HashSet<>();
        String[] features = specialFeatures.split(",");
        for (String feature : features) {
            result.add(Feature.getFeatureByValue(feature));
        }

        result.remove(null);
        return result;
    }

    public void setSpecialFeatures(Set<Feature> features) {
        if (isNull(features)) {
            specialFeatures = null;
        } else {
            specialFeatures = features.stream().map(Feature::getValue).collect(Collectors.joining(","));
        }

    }
}
