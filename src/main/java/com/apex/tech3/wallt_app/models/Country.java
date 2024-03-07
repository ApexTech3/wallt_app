//package com.apex.tech3.wallt_app.models;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//@Data
//@Entity
//@Table(name = "countries", schema = "wallt_db")
//public class Country {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "country_sequence")
//    @SequenceGenerator(name = "country_sequence", sequenceName = "increment_SEQ", allocationSize = 1)
//    @Column(name = "country_id")
//    private int id;
//    @Column(name = "name")
//    private String name;
//    @Override
//    public String toString() {
//        return name;
//    }
//}
//
