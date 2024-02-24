package com.apex.tech3.wallt_app.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "addresses", schema = "wallt_db")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_sequence")
    @SequenceGenerator(name = "address_sequence", sequenceName = "increment_SEQ", allocationSize = 1)
    @Column(name = "address_id")
    private int id;
    @Column(name = "street")
    private String street;
    @Column(name = "number")
    private int number;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
}
