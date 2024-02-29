package com.apex.tech3.wallt_app.models;

import com.apex.tech3.wallt_app.models.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "transactions", schema = "wallt_db")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_sequence")
    @SequenceGenerator(name = "transaction_sequence", sequenceName = "wallt_db.increment_SEQ", allocationSize = 1)
    @Column(name = "transaction_id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "receiver_wallet")
    private Wallet receiverWallet;
    @ManyToOne
    @JoinColumn(name = "sender_wallet")
    private Wallet senderWallet;
    private BigDecimal amount;
    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    @CreationTimestamp
    @Column(name = "stamp_created", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp stampCreated;
}
