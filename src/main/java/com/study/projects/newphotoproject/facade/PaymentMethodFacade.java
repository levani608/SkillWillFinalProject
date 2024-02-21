package com.study.projects.newphotoproject.facade;

import com.study.projects.newphotoproject.model.domain.database.PaymentMethodEntity;
import com.study.projects.newphotoproject.model.dto.PaymentMethodDto;
import com.study.projects.newphotoproject.model.dto.PaymentMethodShortDto;
import com.study.projects.newphotoproject.model.enums.PaymentMethodStatus;
import com.study.projects.newphotoproject.model.mapper.PaymentMethodMapper;
import com.study.projects.newphotoproject.model.param.AddModifyPaymentMethodParam;
import com.study.projects.newphotoproject.model.param.filterparams.PaymentMethodFilterParam;
import com.study.projects.newphotoproject.service.PaymentMethodService;
import com.study.projects.newphotoproject.service.UserWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentMethodFacade {

    private final PaymentMethodService paymentMethodService;
    private final UserWalletService userWalletService;
    private final BeanFactory beanFactory;

    public List<PaymentMethodDto> getPaymentMethods() {

        return paymentMethodService.getPaymentMethods().stream()
                .map(PaymentMethodMapper::toPaymentMethodDto).toList();

    }

    public List<PaymentMethodDto> getFilteredPaymentMethods(PaymentMethodFilterParam paymentMethodFilterParam) {

        return paymentMethodService.getFilteredPaymentMethods(paymentMethodFilterParam).stream()
                .map(PaymentMethodMapper::toPaymentMethodDto).toList();
    }

    public PaymentMethodDto addPaymentMethod(AddModifyPaymentMethodParam addModifyPaymentMethodParam) {

        PaymentMethodEntity paymentMethod = new PaymentMethodEntity();

        paymentMethod.setPaymentMethodName(addModifyPaymentMethodParam.getPaymentMethodName().toUpperCase());
        paymentMethod.setPaymentRate(addModifyPaymentMethodParam.getPaymentRate());
        paymentMethod.setPaymentMethodStatus(PaymentMethodStatus.ACTIVE);

        paymentMethod = paymentMethodService.savePaymentMethod(paymentMethod);

        return PaymentMethodMapper.toPaymentMethodDto(paymentMethod);
    }

    public PaymentMethodDto modifyPaymentMethod(Long paymentMethodId, AddModifyPaymentMethodParam addModifyPaymentMethodParam) {

        PaymentMethodEntity paymentMethodEntity = paymentMethodService.findByPaymentMethodId(paymentMethodId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found!"));;

        paymentMethodEntity.setPaymentMethodName(addModifyPaymentMethodParam.getPaymentMethodName());
        paymentMethodEntity.setPaymentRate(addModifyPaymentMethodParam.getPaymentRate());

        paymentMethodService.savePaymentMethod(paymentMethodEntity);

        return PaymentMethodMapper.toPaymentMethodDto(paymentMethodEntity);
    }

    public PaymentMethodDto blockPaymentMethod(Long paymentMethodId) {

        PaymentMethodEntity paymentMethodEntity = paymentMethodService.findByPaymentMethodId(paymentMethodId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found!"));

        if (paymentMethodEntity.getPaymentMethodStatus() == PaymentMethodStatus.BLOCKED)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Payment method already blocked!");

        paymentMethodEntity.setPaymentMethodStatus(PaymentMethodStatus.BLOCKED);

        paymentMethodService.savePaymentMethod(paymentMethodEntity);

        //userWalletService.blockWallets(paymentMethodEntity);

        return PaymentMethodMapper.toPaymentMethodDto(paymentMethodEntity);
    }

    public PaymentMethodDto activatePaymentMethod(Long paymentMethodId) {

        PaymentMethodEntity paymentMethodEntity = paymentMethodService.findByPaymentMethodId(paymentMethodId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found!"));

        if (paymentMethodEntity.getPaymentMethodStatus() == PaymentMethodStatus.ACTIVE)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Payment method already active!");

        paymentMethodEntity.setPaymentMethodStatus(PaymentMethodStatus.ACTIVE);

        paymentMethodService.savePaymentMethod(paymentMethodEntity);

        //userWalletService.activateWallets(paymentMethodEntity);

        return PaymentMethodMapper.toPaymentMethodDto(paymentMethodEntity);
    }




    public List<PaymentMethodShortDto> getPaymentMethodsShort() {

        return paymentMethodService.getPaymentMethods().stream().map(PaymentMethodMapper::toPaymentMethodShortDto).toList();

    }
}
