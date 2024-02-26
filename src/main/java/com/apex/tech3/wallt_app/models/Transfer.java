package com.apex.tech3.wallt_app.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "transfers", schema = "wallt_db")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transfer_sequence")
    @SequenceGenerator(name = "transfer_sequence", sequenceName = "increment_SEQ", allocationSize = 1)
    @Column(name = "transfer_id")
    private int id;
    @Column(name = "card_id", nullable = false)
    private int cardId;

    @Column(name = "amount", nullable = false)
    private long amount;

    @Column(name = "status", nullable = false)
    private boolean status;

    @Column(name = "currency_id", nullable = false)
    private int currencyId;

    @Column(name = "wallet_id", nullable = false)
    private int walletId;

    @Column(name = "direction", nullable = false, length = 20)
    private String direction;


}
