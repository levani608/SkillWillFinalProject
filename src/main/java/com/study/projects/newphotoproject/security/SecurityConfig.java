package com.study.projects.newphotoproject.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
        @Qualifier("JwtBasedAuthenticationFilter") OncePerRequestFilter authenticationProcessingFilter) throws Exception {
        http
                .addFilterBefore(authenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(x->x.requestMatchers(
                        "/login",
                        "/register",
                        "**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/v2/api-docs/**",
                        "/webjars/**",
                        "/swagger-resources/**").permitAll().anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }



}



