package com.study.projects.newphotoproject.model.dto;

import com.study.projects.newphotoproject.model.enums.UserStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
public class UserDetailDto extends UserDto {

    private Long userId;

    private Double balance;
    private UserStatus userStatus;

    public UserDetailDto(Long userId, String username, String firstName, String lastName, String email, UserStatus userStatus,Double balance) {
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userStatus = userStatus;
        this.balance=balance;
    }

}
