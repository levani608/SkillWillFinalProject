package com.study.projects.newphotoproject.model.domain.database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.Set;


@Entity(name = "wallet_transactions")
@Getter
@Setter
@RequiredArgsConstructor
public class WalletTransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @CreationTimestamp
    protected LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "user_wallet_id")
    private UserWalletEntity userWalletEntity;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @OneToMany(mappedBy = "transactionEntity")
    private Set<InvoiceEntity> invoices;

}
