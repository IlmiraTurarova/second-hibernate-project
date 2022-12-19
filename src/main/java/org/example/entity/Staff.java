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
@Table(name="staff", schema = "movie")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="staff_id")
    private Byte id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @ManyToOne
    @JoinColumn(name="address_id")
    private Address address;

    @Lob
    @Column(name="picture", columnDefinition = "BLOB")
    private byte[] picture;

    @Column(name="email")
    private String email;

    @ManyToOne
    @JoinColumn(name="store_id")
    private Store store;

    @Column(name="active", columnDefinition = "BIT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean active;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;
}
