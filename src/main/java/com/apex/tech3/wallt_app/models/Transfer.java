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


}
