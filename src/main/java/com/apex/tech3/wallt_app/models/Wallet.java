package com.apex.tech3.wallt_app.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "wallets", schema = "wallt_db")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallet_sequence")
    @SequenceGenerator(name = "wallet_sequence", sequenceName = "increment_SEQ", allocationSize = 1)
    @Column(name = "wallet_id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "holder_id")
    private User holder;
    @Column(name = "amount")
    private BigDecimal amount;
    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;
    @Column(name = "stamp_created")
    private Timestamp stampCreated;
}
