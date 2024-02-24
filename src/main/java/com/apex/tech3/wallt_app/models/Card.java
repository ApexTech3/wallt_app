package com.apex.tech3.wallt_app.models;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "cards", schema = "wallt_db")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_sequence")
    @SequenceGenerator(name = "card_sequence", sequenceName = "increment_SEQ", allocationSize = 1)
    @Column(name = "card_id")
    private int id;

    @Column(name = "number")
    private String number;
    @Column(name = "expiration_date")
    private LocalDate expirationDate;
    @Column(name = "cvv")
    private String cvv;
    @ManyToOne
    @JoinColumn(name = "holder_id")
    private User holder;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public User getHolder() {
        return holder;
    }

    public void setHolder(User holder) {
        this.holder = holder;
    }
}
