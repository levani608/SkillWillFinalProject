package com.study.projects.newphotoproject.service;

import com.study.projects.newphotoproject.model.domain.database.PaymentMethodEntity;
import com.study.projects.newphotoproject.model.domain.database.UserEntity;
import com.study.projects.newphotoproject.model.domain.database.UserWalletEntity;
import com.study.projects.newphotoproject.model.dto.UserWalletDto;
import com.study.projects.newphotoproject.model.enums.UserWalletStatus;
import com.study.projects.newphotoproject.repository.UserWalletRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserWalletService {

    private final UserWalletRepository userWalletRepository;

    public List<UserWalletEntity> getAllUserWallets(Long userId) {
        return userWalletRepository.findAllByWalletOwnerEntityUserId(userId);
    }

    @PostAuthorize("hasRole('ADMIN') or (returnObject.isPresent() and @UserValidator.checkOwnership(returnObject.get().walletOwnerEntity.id))")
    public Optional<UserWalletEntity> findUserWalletById(Long userWalletId) {
        return userWalletRepository.findById(userWalletId);
    }

    public Optional<UserWalletEntity> getUserWalletById(Long userWalletId) {
        return userWalletRepository.findById(userWalletId);
    }

    public UserWalletEntity saveUserWallet(UserWalletEntity userWallet) {
        return userWalletRepository.save(userWallet);
    }

    public void userDeactivate(UserEntity user) {
        List<UserWalletEntity> userWallets = userWalletRepository.findAllByWalletOwnerEntity(user).stream()
                .filter(uw-> uw.getUserWalletStatus()==UserWalletStatus.ACTIVE).toList();

        for (UserWalletEntity userWallet : userWallets) {
            userWallet.setUserWalletStatus(UserWalletStatus.USERDEACTIVATED);
        }

        userWalletRepository.saveAllAndFlush(userWallets);
    }

    public void userActivate(UserEntity user) {
        List<UserWalletEntity> userWallets = userWalletRepository.findAllByWalletOwnerEntity(user).stream()
                .filter(uw-> uw.getUserWalletStatus()==UserWalletStatus.USERDEACTIVATED).toList();

        for (UserWalletEntity userWallet : userWallets) {
            userWallet.setUserWalletStatus(UserWalletStatus.ACTIVE);
        }

        userWalletRepository.saveAllAndFlush(userWallets);
    }

    public void blockWallets(PaymentMethodEntity paymentMethodEntity) {
        List<UserWalletEntity> userWallets = userWalletRepository.findAllByPaymentMethodEntity(paymentMethodEntity).stream()
                .filter(uw-> uw.getUserWalletStatus()==UserWalletStatus.ACTIVE).toList();

        for (UserWalletEntity userWallet : userWallets) {
            userWallet.setUserWalletStatus(UserWalletStatus.PAYMENTMETHODBLOCKED);
        }

        userWalletRepository.saveAllAndFlush(userWallets);
    }

    public void activateWallets(PaymentMethodEntity paymentMethodEntity) {
        List<UserWalletEntity> userWallets = userWalletRepository.findAllByPaymentMethodEntity(paymentMethodEntity).stream()
                .filter(uw-> uw.getUserWalletStatus()==UserWalletStatus.PAYMENTMETHODBLOCKED).toList();

        for (UserWalletEntity userWallet : userWallets) {
            userWallet.setUserWalletStatus(UserWalletStatus.ACTIVE);
        }

        userWalletRepository.saveAllAndFlush(userWallets);
    }


    public Optional<UserWalletEntity> getActiveUserWalletByUserId(Long userId) {
        return userWalletRepository.findByWalletOwnerIdActive(userId);
    }
}
