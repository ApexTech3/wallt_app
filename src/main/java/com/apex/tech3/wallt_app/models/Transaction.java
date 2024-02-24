package com.apex.tech3.wallt_app.models;

import com.apex.tech3.wallt_app.models.enums.StatusEnum;
import jakarta.persistence.*;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Wallet getReceiverWallet() {
        return receiverWallet;
    }

    public void setReceiverWallet(Wallet receiverWallet) {
        this.receiverWallet = receiverWallet;
    }

    public Wallet getSenderWallet() {
        return senderWallet;
    }

    public void setSenderWallet(Wallet senderWallet) {
        this.senderWallet = senderWallet;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
