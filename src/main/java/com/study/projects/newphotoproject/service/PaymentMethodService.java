package com.study.projects.newphotoproject.service;

import com.study.projects.newphotoproject.model.domain.database.PaymentMethodEntity;
import com.study.projects.newphotoproject.model.dto.PaymentMethodDto;
import com.study.projects.newphotoproject.model.enums.PaymentMethodStatus;
import com.study.projects.newphotoproject.model.param.filterparams.PaymentMethodFilterParam;
import com.study.projects.newphotoproject.repository.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;

    public List<PaymentMethodEntity> getPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    public PaymentMethodEntity savePaymentMethod(PaymentMethodEntity paymentMethod) {
        return paymentMethodRepository.saveAndFlush(paymentMethod);
    }

    public Optional<PaymentMethodEntity> findByPaymentMethodName(String name) {
        return paymentMethodRepository.findByPaymentMethodName(name);
    }
    public Optional<PaymentMethodEntity> findByPaymentMethodId(Long id) {
        return paymentMethodRepository.findByPaymentMethodId(id);
    }

    public List<PaymentMethodEntity> getFilteredPaymentMethods(PaymentMethodFilterParam paymentMethodFilterParam) {

        List<Long> ids = new ArrayList<>();
        List<Double> rates = new ArrayList<>();
        List<String> statuses = new ArrayList<>();

        if (paymentMethodFilterParam.getPaymentMethodIds() != null)
            ids = paymentMethodFilterParam.getPaymentMethodIds();
        if (paymentMethodFilterParam.getPaymentRates() != null)
            rates = paymentMethodFilterParam.getPaymentRates();
        if (paymentMethodFilterParam.getPaymentMethodStatuses() != null)
            statuses = paymentMethodFilterParam.getPaymentMethodStatuses().stream().map(Enum::toString).toList();

        if  (paymentMethodFilterParam.getPaymentMethodName() != null)
            paymentMethodFilterParam.setPaymentMethodName("%" + paymentMethodFilterParam.getPaymentMethodName().toUpperCase() + "%");

        /*if (paymentMethodFilterParam.getCreatedFrom() == null)
            paymentMethodFilterParam.setCreatedFrom(LocalDate.of(1970, 1, 1));
        if (paymentMethodFilterParam.getCreatedTo() == null)
            paymentMethodFilterParam.setCreatedTo(LocalDate.now());
        if (paymentMethodFilterParam.getUpdatedFrom() == null)
            paymentMethodFilterParam.setUpdatedFrom(paymentMethodFilterParam.getCreatedFrom());
        if (paymentMethodFilterParam.getUpdatedTo() == null)
            paymentMethodFilterParam.setUpdatedTo(LocalDate.now());*/

        return paymentMethodRepository.findFilteredPaymentMethods(
                ids,/*paymentMethodFilterParam.getPaymentMethodIds(),*/
                paymentMethodFilterParam.getPaymentMethodName(),
                rates,/*paymentMethodFilterParam.getPaymentRates(),*/
                statuses,/*paymentMethodFilterParam.getPaymentMethodStatuses(),*/
                paymentMethodFilterParam.getCreatedFrom(),
                paymentMethodFilterParam.getCreatedTo(),
                paymentMethodFilterParam.getUpdatedFrom(),
                paymentMethodFilterParam.getUpdatedTo());
    }

    /*public PaymentMethodEntity blockPaymentMethod(PaymentMethodEntity paymentMethodEntity) {

        paymentMethodEntity.setPaymentMethodStatus(PaymentMethodStatus.BLOCKED);

        paymentMethodRepository.saveAndFlush(paymentMethodEntity);

        return paymentMethodEntity;
    }

    public PaymentMethodEntity activatePaymentMethod(PaymentMethodEntity paymentMethodEntity) {

        paymentMethodEntity.setPaymentMethodStatus(PaymentMethodStatus.ACTIVE);

        paymentMethodRepository.saveAndFlush(paymentMethodEntity);

        return paymentMethodEntity;
    }*/
}
