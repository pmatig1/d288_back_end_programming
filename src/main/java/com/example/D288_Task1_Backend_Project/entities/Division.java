package com.example.D288_Task1_Backend_Project.entities;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "divisions")
@Getter
@Setter
public class Division {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "division_id")
    private Long id;

    @Column(name = "division", nullable = false)
    private String division;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false, updatable = false)
    private Date create_date;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update", nullable = false)
    private Date last_update;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @Column(name = "country_id", insertable = false, updatable = false)
    private Long country_id;

    @OneToMany(mappedBy = "division")
    private Set<Customer> customers = new HashSet<>();

    public Division() {
    }

    public void setCountry(Country country) {
        this.country = country;
        this.country_id = country == null ? null : country.getId();
    }

    @PrePersist
    protected void onCreate() {
        Date now = new Date();
        create_date = now;
        last_update = now;
    }

    @PreUpdate
    protected void onUpdate() { last_update = new Date(); }
}
