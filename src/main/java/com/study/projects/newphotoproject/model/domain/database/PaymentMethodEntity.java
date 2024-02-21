package com.study.projects.newphotoproject.model.domain.database;

import com.study.projects.newphotoproject.model.enums.PaymentMethodStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity(name = "payment_methods")
@Getter
@Setter
@RequiredArgsConstructor
public class PaymentMethodEntity extends TableDates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_method_id")
    private Long paymentMethodId;

    @Column(name = "payment_method_name",nullable = false, unique = true)
    private String paymentMethodName;

    @Column(name = "payment_rate",nullable = false)
    private Double paymentRate;

    @Column(name = "payment_method_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethodStatus paymentMethodStatus;

    @OneToMany(mappedBy = "paymentMethodEntity")
    private Set<UserWalletEntity> wallets;

}
