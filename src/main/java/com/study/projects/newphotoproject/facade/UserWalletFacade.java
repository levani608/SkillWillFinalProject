package com.study.projects.newphotoproject.facade;

import com.study.projects.newphotoproject.model.domain.database.PaymentMethodEntity;
import com.study.projects.newphotoproject.model.domain.database.UserEntity;
import com.study.projects.newphotoproject.model.domain.database.UserWalletEntity;
import com.study.projects.newphotoproject.model.domain.database.WalletTransactionEntity;
import com.study.projects.newphotoproject.model.dto.UserWalletDetailDto;
import com.study.projects.newphotoproject.model.dto.UserWalletDto;
import com.study.projects.newphotoproject.model.dto.WalletTransactionDto;
import com.study.projects.newphotoproject.model.enums.PaymentMethodStatus;
import com.study.projects.newphotoproject.model.enums.UserStatus;
import com.study.projects.newphotoproject.model.enums.UserWalletStatus;
import com.study.projects.newphotoproject.model.mapper.UserWalletMapper;
import com.study.projects.newphotoproject.model.mapper.WalletTransactionMapper;
import com.study.projects.newphotoproject.model.param.AddWalletParam;
import com.study.projects.newphotoproject.model.param.FillUserWalletParam;
import com.study.projects.newphotoproject.service.PaymentMethodService;
import com.study.projects.newphotoproject.service.UserService;
import com.study.projects.newphotoproject.service.UserWalletService;
import com.study.projects.newphotoproject.service.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserWalletFacade {

    private final UserWalletService userWalletService;

    private final WalletTransactionService walletTransactionService;

    private final PaymentMethodService paymentMethodService;

    private final UserService userService;

    @Transactional
    public UserWalletDto getActiveUserWallet(Long userId) {

        UserWalletEntity userWallet = userWalletService.getActiveUserWalletByUserId(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet not found!"));

        if (userWallet.getWalletOwnerEntity().getUserStatus() == UserStatus.DEACTIVATED)
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User deactivated!");

        return UserWalletMapper.toUserWalletDetailDto(userWallet);
    }



    public List<WalletTransactionDto> getAllWalletTransactions(Long userWalletId) {

        UserWalletEntity userWallet = userWalletService.findUserWalletById(userWalletId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet not found!"));

        if (userWallet.getWalletOwnerEntity().getUserStatus() == UserStatus.DEACTIVATED)
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User deactivated!");

        return userWallet.getWalletTransactions().stream()
                .map(WalletTransactionMapper::toWalletTransactionDto).toList();
    }

    public UserWalletDetailDto addWallet(Long userId, AddWalletParam addWalletParam) {

        UserEntity user = userService.findByUserId(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        String paymentMethodName = addWalletParam.getPaymentMethodName().toUpperCase();
        PaymentMethodEntity paymentMethod = paymentMethodService.findByPaymentMethodName(paymentMethodName).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment method not found"));

        if (user.getUserStatus()== UserStatus.DEACTIVATED) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User deactivated!");
        } else if (paymentMethod.getPaymentMethodStatus()== PaymentMethodStatus.BLOCKED) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Payment method blocked!");
        }

        List<UserWalletEntity> userWallets = user.getUserWallets().stream()
                .filter(w -> w.getUserWalletStatus() == UserWalletStatus.ACTIVE).toList();

        if (!userWallets.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User already has an active wallet!");

        UserWalletEntity userWallet = new UserWalletEntity();
        WalletTransactionEntity walletTransaction = new WalletTransactionEntity();

        userWallet.setPaymentMethodEntity(paymentMethod);
        userWallet.setBalance(addWalletParam.getBalance());
        userWallet.setWalletOwnerEntity(user);
        userWallet.setUserWalletStatus(UserWalletStatus.ACTIVE);
        userWalletService.saveUserWallet(userWallet);

        walletTransaction.setUserWalletEntity(userWallet);
        walletTransaction.setAmount(addWalletParam.getBalance());
        walletTransactionService.saveWalletTransaction(walletTransaction);

        return UserWalletMapper.toUserWalletDetailDto(userWallet);
    }

    @Transactional
    public UserWalletDto fillWalletBalance(FillUserWalletParam fillUserWalletParam) {

        Double amount = fillUserWalletParam.getAmount();
        WalletTransactionEntity walletTransaction = new WalletTransactionEntity();

        UserWalletEntity userWallet = userWalletService.findUserWalletById(fillUserWalletParam.getUserWalletId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet not found!"));

        if (userWallet.getUserWalletStatus()==UserWalletStatus.USERDEACTIVATED) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User deactivated!");
        }
        else if (userWallet.getUserWalletStatus()==UserWalletStatus.PAYMENTMETHODBLOCKED) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Payment method blocked!");
        }
        else if (userWallet.getUserWalletStatus()==UserWalletStatus.DELETED) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet deleted");
        }

        userWallet.setBalance(userWallet.getBalance()+ amount);
        userWalletService.saveUserWallet(userWallet);

        walletTransaction.setUserWalletEntity(userWallet);
        walletTransaction.setAmount(amount);
        walletTransactionService.saveWalletTransaction(walletTransaction);

        return UserWalletMapper.toUserWalletDto(userWallet);
    }

    @Transactional
    public UserWalletDetailDto deleteWallet(Long userWalletId) {

        UserWalletEntity userWallet = userWalletService.findUserWalletById(userWalletId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet not found!"));

        if (userWallet.getUserWalletStatus() == UserWalletStatus.USERDEACTIVATED)
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User deactivated!");
        else if (userWallet.getUserWalletStatus() == UserWalletStatus.DELETED)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User wallet not found!");

        userWallet.setUserWalletStatus(UserWalletStatus.DELETED);
        userWalletService.saveUserWallet(userWallet);

        return UserWalletMapper.toUserWalletDetailDto(userWallet);
    }
}
