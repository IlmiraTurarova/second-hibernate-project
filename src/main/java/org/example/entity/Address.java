package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="address", schema = "movie")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="address_id")
    private Short id;

    @Column(name="address")
    private String address;

    @Column(name="address2")
    private String address2;

    @Column(name="district")
    private String district;

    @ManyToOne
    @JoinColumn(name="city_id")
    private City city;

    @Column(name="postal_code")
    private String postalCode;

    @Column(name="phone")
    private String phone;

    //@Column(name="location", columnDefinition = "Geometry")
    //@Type(type = "org.hibernate.spatial.GeometryType")
    //private Geometry location;

    @Column(name="last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;
}
