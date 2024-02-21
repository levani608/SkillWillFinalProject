package com.study.projects.newphotoproject.service;

import com.study.projects.newphotoproject.model.domain.database.WalletTransactionEntity;
import com.study.projects.newphotoproject.repository.WalletTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletTransactionService {

    private final WalletTransactionRepository walletTransactionRepository;

    public WalletTransactionEntity saveWalletTransaction(WalletTransactionEntity walletTransaction) {
        return walletTransactionRepository.save(walletTransaction);
    }

    public List<WalletTransactionEntity> findAllTransaction() {
        return walletTransactionRepository.findAll();
    }

    public List<WalletTransactionEntity> findTransactionsByWalletId(Long walletId) {
        return walletTransactionRepository.findAllByUserWalletEntityUserWalletId(walletId);
    }
}
