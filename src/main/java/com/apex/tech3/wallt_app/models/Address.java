package com.apex.tech3.wallt_app.models;

import jakarta.persistence.*;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
