package com.study.projects.newphotoproject.facade;

import com.study.projects.newphotoproject.model.dto.ResponseTokensDto;
import com.study.projects.newphotoproject.model.dto.UserDetailDto;
import com.study.projects.newphotoproject.model.param.LoginParam;
import com.study.projects.newphotoproject.model.param.SignUpParam;
import com.study.projects.newphotoproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthFacade {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    public static final String secret="19239129319ASDASASasdasdasda11ff";

    public UserDetailDto singUp(SignUpParam signUpParam) {
        return null;
    }

    public ResponseTokensDto login(LoginParam loginParam) {
        return null;
    }
    public Authentication authenticate(String token) {
        return null;
    }


}
