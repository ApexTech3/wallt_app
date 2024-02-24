package com.apex.tech3.wallt_app.models;

import jakarta.persistence.*;

@Entity
@Table(name = "roles", schema = "wallt_db")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_sequence")
    @SequenceGenerator(name = "role_sequence", sequenceName = "increment_SEQ", allocationSize = 1)
    @Column(name = "role_id")
    private int id;
    @Column(name = "name")
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
