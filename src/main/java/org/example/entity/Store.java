package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="store", schema = "movie")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="store_id")
    private Byte id;

    @OneToOne
    @JoinColumn(name="manager_staff_id")
    private Staff staff;

    @OneToOne
    @JoinColumn(name="address_id")
    private Address address;

    @Column(name="last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;
}
