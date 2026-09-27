package com.example.D288_Task1_Backend_Project.entities;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vacations")
@Getter
@Setter
public class Vacation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacation_id")
    private Long id;

    @Column(name = "vacation_title", nullable = false)
    private String vacation_title;

    @Column(nullable = false)
    private String description;

    @Column(name = "travel_fare_price", nullable = false)
    private BigDecimal travel_price;

    @Column(name = "image_url", nullable = false)
    private String image_url;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false, updatable = false)
    private Date create_date;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update", nullable = false)
    private Date last_update;

    @OneToMany(mappedBy = "vacation")
    private Set<Excursion> excursions = new HashSet<>();

    public Vacation() {
    }

    @PrePersist
    protected void onCreate() {
        Date now = new Date();
        create_date = now;
        last_update = now;
    }

    @PreUpdate
    protected void onUpdate() {
        last_update = new Date();
    }
}