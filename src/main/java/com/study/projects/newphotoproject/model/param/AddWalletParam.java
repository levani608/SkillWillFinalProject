package com.study.projects.newphotoproject.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddWalletParam {

    private String paymentMethodName;

    private Double balance;

}
