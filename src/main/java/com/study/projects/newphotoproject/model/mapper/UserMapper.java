package com.study.projects.newphotoproject.model.mapper;

import com.study.projects.newphotoproject.model.domain.database.UserEntity;
import com.study.projects.newphotoproject.model.dto.UserDetailDto;

public class UserMapper {


    public static UserDetailDto toUserDetailDto(UserEntity userEntity) {
        return new UserDetailDto(userEntity.getId(), userEntity.getUsername(), userEntity.getFirstName(),
                userEntity.getLastName(), userEntity.getEmail(), userEntity.getUserStatus());
    }

}
