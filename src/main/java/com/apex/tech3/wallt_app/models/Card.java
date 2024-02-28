package com.apex.tech3.wallt_app.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "cards", schema = "wallt_db")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_sequence")
    @SequenceGenerator(name = "card_sequence", sequenceName = "wallt_db.increment_seq", allocationSize = 1)
    @Column(name = "card_id")
    private int id;
    @Column(name = "first_last_name")
    private String cardHolderName;
    @Column(name = "number")
    private String number;
    @Column(name = "expiration_month")
    private String expirationMonth;
    @Column(name = "expiration_year")
    private String expirationYear;
    @Column(name = "cvv")
    private String cvv;
    @ManyToOne
    @JoinColumn(name = "holder_id")
    private User holder;
    @CreationTimestamp
    @Column(name = "stamp_created")
    private Timestamp stampCreated;
    @Column(name = "is_active")
    private boolean isActive;
}
