package com.example.glassify.service;

import com.example.glassify.model.User;
import com.example.glassify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public boolean authenticate(String rawPassword, String storedHashedPassword) {
        return passwordEncoder.matches(rawPassword, storedHashedPassword);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
