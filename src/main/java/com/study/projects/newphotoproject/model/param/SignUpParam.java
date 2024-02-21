package com.study.projects.newphotoproject.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class SignUpParam {

    @Pattern(regexp = "^[a-zA-Z0-9]{3,25}$")
    private String username;

    private String firstName;

    private String lastName;

    @NotBlank
    @Email
    @Pattern(regexp = "^[a-zA-Z0-9@.]{5,30}$")
    private String email;

    @NotBlank
    @Size(min = 8, max = 60)
    private String password;

}
