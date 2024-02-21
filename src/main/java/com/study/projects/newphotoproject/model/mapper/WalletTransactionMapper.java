package com.study.projects.newphotoproject.model.mapper;

import com.study.projects.newphotoproject.model.domain.database.WalletTransactionEntity;
import com.study.projects.newphotoproject.model.dto.WalletTransactionDto;

public class WalletTransactionMapper {

    public static WalletTransactionDto toWalletTransactionDto(WalletTransactionEntity walletTransactionEntity) {
        return new WalletTransactionDto(walletTransactionEntity.getTransactionId(), walletTransactionEntity.getCreatedAt(),
                walletTransactionEntity.getAmount());
    }

}
