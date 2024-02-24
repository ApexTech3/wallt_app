package com.apex.tech3.wallt_app.models;

import com.apex.tech3.wallt_app.models.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "transactions", schema = "wallt_db")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_sequence")
    @SequenceGenerator(name = "transaction_sequence", sequenceName = "increment_SEQ", allocationSize = 1)
    @Column(name = "transaction_id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "receiver_wallet")
    private Wallet receiverWallet;
    @ManyToOne
    @JoinColumn(name = "sender_wallet")
    private Wallet senderWallet;
    private long amount;
    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
}
