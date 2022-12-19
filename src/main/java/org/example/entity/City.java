package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="city", schema = "movie")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="city_id")
    private Short id;

    @Column(name="city")
    private String city;

    @ManyToOne
    @JoinColumn(name="country_id")
    private Country country;

    @Column(name="last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;
}
