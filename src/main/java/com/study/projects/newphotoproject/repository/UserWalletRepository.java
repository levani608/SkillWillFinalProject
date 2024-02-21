package com.study.projects.newphotoproject.repository;

import com.study.projects.newphotoproject.model.domain.database.PaymentMethodEntity;
import com.study.projects.newphotoproject.model.domain.database.UserEntity;
import com.study.projects.newphotoproject.model.domain.database.UserWalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserWalletRepository extends JpaRepository<UserWalletEntity, Long> {

    /*@Query("select u from user_wallets u where u.walletOwnerEntity.userId = (:ownerId)")
    List<UserWalletEntity> findUserWalletsByOwnerId(@QueryParam("ownerId") Long ownerId);*/

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<UserWalletEntity> findById(Long id);

    List<UserWalletEntity> findAllByWalletOwnerEntityUserId(Long ownerId);

    List<UserWalletEntity> findAllByWalletOwnerEntity(UserEntity user);

    List<UserWalletEntity> findAllByPaymentMethodEntity(PaymentMethodEntity paymentMethod);


    @Query("select w from user_wallets w where w.walletOwnerEntity.id = :ownerId and w.userWalletStatus = 'ACTIVE'")
    Optional<UserWalletEntity> findByWalletOwnerIdActive(Long ownerId);
}
