package com.study.projects.newphotoproject.model.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class LoginParam {

    @Pattern(regexp = "^[a-zA-Z0-9]{3,25}$")
    private String username;

    @NotBlank
    @Size(min = 8, max = 60)
    private String password;

}
