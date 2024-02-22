package com.study.projects.newphotoproject.model.param;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;



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
