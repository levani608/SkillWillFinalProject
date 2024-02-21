package com.study.projects.newphotoproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service("AuthService")
@RequiredArgsConstructor
public class AuthService {

    public long getPrincipalDatabaseId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt credentials = (Jwt) authentication.getCredentials();
        Long databaseId = (Long) credentials.getClaims().get("database_id");
        if (databaseId == null) throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User Id not Found!");
        return databaseId;
    }



}
