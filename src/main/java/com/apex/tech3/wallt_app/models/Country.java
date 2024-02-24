package com.apex.tech3.wallt_app.models;


import jakarta.persistence.*;

@Entity
@Table(name = "countries", schema = "wallt_db")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "country_sequence")
    @SequenceGenerator(name = "country_sequence", sequenceName = "increment_SEQ", allocationSize = 1)
    @Column(name = "country_id")
    private int id;
    @Column(name = "name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

