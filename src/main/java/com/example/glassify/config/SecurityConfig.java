package com.example.glassify.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecretKey secretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(org.springframework.security.config.annotation.web.builders.HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Allow all requests without authentication
                );
        return http.build();
    }
}
