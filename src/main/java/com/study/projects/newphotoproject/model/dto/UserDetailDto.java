package com.study.projects.newphotoproject.model.dto;

import com.study.projects.newphotoproject.model.domain.database.UserEntity;
import com.study.projects.newphotoproject.model.enums.UserStatus;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@NoArgsConstructor
public class UserDetailDto extends UserDto {

    private Long userId;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    public UserDetailDto(Long userId, String username, String firstName, String lastName, String email, UserStatus userStatus) {
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userStatus = userStatus;
    }

}
