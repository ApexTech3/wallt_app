package com.apex.tech3.wallt_app.models;

import jakarta.persistence.*;

@Entity
@Table(name = "cities", schema = "wallt_db")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_sequence")
    @SequenceGenerator(name = "city_sequence", sequenceName = "increment_SEQ", allocationSize = 1)
    @Column(name = "city_id")
    private int id;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
}
