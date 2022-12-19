package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="rental", schema = "movie")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="rental_id")
    private Integer id;

    @Column(name="rental_date")
    private LocalDateTime rentalDate;

    @ManyToOne
    @JoinColumn(name="inventory_id")
    private Inventory inventory;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @Column(name="return_date")
    private LocalDateTime returnDate;

    @ManyToOne
    @JoinColumn(name="staff_id")
    private Staff staff;

    @Column(name="last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;
}
