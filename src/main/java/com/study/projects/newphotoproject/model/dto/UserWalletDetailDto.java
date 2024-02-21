package com.study.projects.newphotoproject.model.dto;

import com.study.projects.newphotoproject.model.enums.UserWalletStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class UserWalletDetailDto extends UserWalletDto{

    @Enumerated(EnumType.STRING)
    private UserWalletStatus userWalletStatus;

    public UserWalletDetailDto(String paymentMethodName, Double balance, UserWalletStatus userWalletStatus) {
        this.paymentMethodName = paymentMethodName;
        this.balance = balance;
        this.userWalletStatus = userWalletStatus;
    }

}
