package com.study.projects.newphotoproject.model.dto;

import com.study.projects.newphotoproject.model.domain.database.UserEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    protected String username;

    protected String firstName;

    protected String lastName;

    protected String email;

}
