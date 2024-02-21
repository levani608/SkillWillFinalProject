package com.study.projects.newphotoproject.model.domain.database;

import com.study.projects.newphotoproject.model.enums.UserWalletStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity(name = "user_wallets")
@Getter
@Setter
@RequiredArgsConstructor
public class UserWalletEntity extends TableDates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_wallet_id")
    private Long userWalletId;

    @ManyToOne
    @JoinColumn(name = "wallet_owner_id")
    private UserEntity walletOwnerEntity;

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethodEntity paymentMethodEntity;

    @Column(name = "wallet_balance",nullable = false)
    private Double balance;

    @Column(name = "user_wallet_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserWalletStatus userWalletStatus;

    @OneToMany(mappedBy = "userWalletEntity")
    private Set<WalletTransactionEntity> walletTransactions;

}
