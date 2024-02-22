package com.study.projects.newphotoproject.util;

import com.study.projects.newphotoproject.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("UserValidator")
@RequiredArgsConstructor
public class UserValidator {


    public boolean checkOwnership(Long userId) {
        return userId == AuthService.getPrincipalDatabaseId();
    }


    public boolean checkIfAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));


    }

}
