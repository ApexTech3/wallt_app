package com.apex.tech3.wallt_app.models;

import com.apex.tech3.wallt_app.models.enums.DirectionEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "transfers", schema = "wallt_db")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transfer_sequence")
    @SequenceGenerator(name = "transfer_sequence", sequenceName = "increment_SEQ", allocationSize = 1)
    @Column(name = "transfer_id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
    @Column(name = "amount", nullable = false)
    private long amount;
    @Column(name = "status", nullable = false)
    private boolean status;
    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;
    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;
    @Column(name = "direction", nullable = false, length = 20)
    private DirectionEnum direction;
    @Column(name = "stamp_created")
    private Timestamp stampCreated;


}
