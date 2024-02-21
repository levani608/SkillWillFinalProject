package com.study.projects.newphotoproject.repository;

import com.study.projects.newphotoproject.model.domain.database.WalletTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransactionEntity, Long> {

    List<WalletTransactionEntity> findAllByUserWalletEntityUserWalletId(Long walletId);

}
