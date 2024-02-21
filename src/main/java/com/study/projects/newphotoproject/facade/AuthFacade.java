package com.study.projects.newphotoproject.facade;

import com.study.projects.newphotoproject.model.domain.database.UserEntity;
import com.study.projects.newphotoproject.model.dto.ResponseTokensDto;
import com.study.projects.newphotoproject.model.dto.UserDetailDto;
import com.study.projects.newphotoproject.model.enums.UserStatus;
import com.study.projects.newphotoproject.model.mapper.ResponseTokensMapper;
import com.study.projects.newphotoproject.model.mapper.UserMapper;
import com.study.projects.newphotoproject.model.param.LoginParam;
import com.study.projects.newphotoproject.model.param.SignUpParam;
import com.study.projects.newphotoproject.security.CustomAuthentication;
import com.study.projects.newphotoproject.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthFacade {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    public static final String secret="19239129319ASDASAS";

    public UserDetailDto singUp(SignUpParam signUpParam) {

        UserEntity user = new UserEntity();
        user.setUsername(signUpParam.getUsername());
        user.setFirstName(signUpParam.getFirstName());
        user.setLastName(signUpParam.getLastName());
        user.setEmail(signUpParam.getEmail());
        user.setPassword(passwordEncoder.encode(signUpParam.getPassword()));
        user.setUserStatus(UserStatus.ACTIVE);
        user = userService.saveUserInDatabase(user);

        return UserMapper.toUserDetailDto(user);
    }

    public ResponseTokensDto login(LoginParam loginParam) {
        UserEntity user = userService.findByUserName(loginParam.getUsername());
        boolean matches = passwordEncoder.matches(loginParam.getPassword(), user.getPassword());
        if (!matches) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Username or password is invalid!;");
        }
        String token = Jwts.builder()
                .claim("username", user.getUsername())
                .claim("id", user.getId())
                .claim("role", user.getRole())
                .claim("firstName", user.getFirstName())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();
        return ResponseTokensMapper.toResponseTokensDto(token,3600L);
    }
    public Authentication authenticate(String token) {
        Jws<Claims> claimsJws = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build().parseSignedClaims(token);
        Claims payload = claimsJws.getPayload();
        String username = payload.get("username", String.class);
        String role = payload.get("role", String.class);
        Long id = payload.get("id", Long.class);
        return new CustomAuthentication(role, username,id);
    }


}
