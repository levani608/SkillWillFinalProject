package com.study.projects.newphotoproject.security;



import com.study.projects.newphotoproject.facade.AuthFacade;
import com.study.projects.newphotoproject.facade.UserFacade;
import com.study.projects.newphotoproject.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Qualifier("JwtBasedAuthenticationFilter")
public class JwtBasedAuthenticationFilter extends OncePerRequestFilter {


    private final AuthFacade authFacade;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authenticationHeader = request.getHeader("authorization");
        if(authenticationHeader==null|| !authenticationHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        String token = authenticationHeader.replace("Bearer ","");
        Authentication authenticate = authFacade.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        filterChain.doFilter(request,response);
    }
}
