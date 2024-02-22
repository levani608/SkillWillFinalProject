package com.study.projects.newphotoproject.model.dto;

import com.study.projects.newphotoproject.model.enums.UserWalletStatus;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserWalletDetailDto extends UserWalletDto{

    private UserWalletStatus userWalletStatus;

    public UserWalletDetailDto(String paymentMethodName, Double balance, UserWalletStatus userWalletStatus) {
        this.paymentMethodName = paymentMethodName;
        this.balance = balance;
        this.userWalletStatus = userWalletStatus;
    }

}
