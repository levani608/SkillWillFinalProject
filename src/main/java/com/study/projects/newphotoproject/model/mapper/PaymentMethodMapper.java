package com.study.projects.newphotoproject.model.mapper;

import com.study.projects.newphotoproject.model.domain.database.PaymentMethodEntity;
import com.study.projects.newphotoproject.model.dto.PaymentMethodDto;
import com.study.projects.newphotoproject.model.dto.PaymentMethodShortDto;

public class PaymentMethodMapper {

    public static PaymentMethodDto toPaymentMethodDto(PaymentMethodEntity paymentMethodEntity) {
        return new PaymentMethodDto(paymentMethodEntity.getPaymentMethodId(), paymentMethodEntity.getPaymentMethodName(), paymentMethodEntity.getPaymentRate(),
                paymentMethodEntity.getPaymentMethodStatus());
    }

    public static PaymentMethodShortDto toPaymentMethodShortDto(PaymentMethodEntity paymentMethodEntity) {
        return new PaymentMethodShortDto(paymentMethodEntity.getPaymentMethodName(), paymentMethodEntity.getPaymentRate());
    }

}
