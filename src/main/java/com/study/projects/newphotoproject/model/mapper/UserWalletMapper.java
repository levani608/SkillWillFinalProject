package com.study.projects.newphotoproject.model.mapper;

import com.study.projects.newphotoproject.model.domain.database.UserWalletEntity;
import com.study.projects.newphotoproject.model.dto.UserWalletDetailDto;
import com.study.projects.newphotoproject.model.dto.UserWalletDto;

public class UserWalletMapper {

    public static UserWalletDto toUserWalletDto(UserWalletEntity userWalletEntity) {
        return new UserWalletDto(userWalletEntity.getPaymentMethodEntity().getPaymentMethodName(), userWalletEntity.getBalance());
    }

    public static UserWalletDetailDto toUserWalletDetailDto(UserWalletEntity userWalletEntity) {
        return new UserWalletDetailDto(userWalletEntity.getPaymentMethodEntity().getPaymentMethodName(), userWalletEntity.getBalance(),
                userWalletEntity.getUserWalletStatus());
    }

}
