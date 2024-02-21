package com.study.projects.newphotoproject.model.domain.database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
@Entity(name = "invoices")
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private Long invoiceId;

    @CreationTimestamp
    protected LocalDate createdAt;

    @OneToOne
    @JoinColumn(name = "user_server_id")
    private UserServerEntity userServerEntity;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private WalletTransactionEntity transactionEntity;

    public InvoiceEntity(UserServerEntity userServer, WalletTransactionEntity walletTransaction) {
        this.userServerEntity = userServer;
        this.transactionEntity = walletTransaction;
    }

}
