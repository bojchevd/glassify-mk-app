package com.example.glassify.web;

import com.example.glassify.model.dto.UserAuthDTO;
import com.example.glassify.model.dto.UserResponseDTO;
import com.example.glassify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(user -> new UserResponseDTO(user.getEmail(), user.getRole().name(), user.getName()))
                .collect(Collectors.toList());
    }
}
