package com.example.securitytest.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "transaction_history")
@Entity
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "account")
    private String account;

    @Column(name = "in_debt")
    private BigDecimal inDebt;

    @Column(name = "have")
    private boolean have;

    @Column(name = "transaction_time")
    private LocalDateTime transactionTime;

}
