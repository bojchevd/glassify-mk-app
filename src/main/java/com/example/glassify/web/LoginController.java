package com.example.glassify.web;

import com.example.glassify.model.User;
import com.example.glassify.model.dto.AuthResponseDTO;
import com.example.glassify.model.dto.UserDTO;
import com.example.glassify.model.enums.Role;
import com.example.glassify.security.JWTUtil;
import com.example.glassify.service.UserService;
import com.example.glassify.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UtilityService utilityService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDto) {
        if (userService.existsByEmail(userDto.getEmail())) { // todo : check
            return ResponseEntity.badRequest().body("Error: Username or Email is already taken!");
        }

        User user = new User();
        user.setEmail(userDto.getEmail());
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(Role.ROLE_ADMIN);

        if (!utilityService.isNullOrEmpty(userDto.getLastName())) {
            user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        }
        if (!utilityService.isNullOrEmpty(userDto.getCity())) {
            user.setCity(userDto.getCity());
        }
        if (!utilityService.isNullOrEmpty(userDto.getShippingAddress())) {
            user.setShippingAddress(userDto.getShippingAddress());
        }
        if (!utilityService.isNullOrEmpty(userDto.getPhoneNumber())) {
            user.setPhoneNumber(userDto.getPhoneNumber());
        }

        userService.saveUser(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> checkCredentials(@RequestBody UserDTO userDto) {
        Optional<User> userOpt = userService.findByEmail(userDto.getEmail());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String rawPassword = userDto.getPassword();
            String encodedPassword = user.getPassword();
            boolean isMatch = passwordEncoder.matches(rawPassword, encodedPassword);

            System.out.println("Raw Password: " + rawPassword);
            System.out.println("Encoded Password: " + encodedPassword);
            System.out.println("Password Match: " + isMatch);
            if (isMatch) {
                String token = jwtUtil.generateToken(user.getEmail());
                String userRole = String.valueOf(user.getRole());
                AuthResponseDTO authResponseDTO = new AuthResponseDTO(token, userRole);
                return ResponseEntity.ok(authResponseDTO);
            } else {
                return ResponseEntity.badRequest().body("Error: Invalid credentials");
            }
        }
        return ResponseEntity.badRequest().body("Error: User not found");
    }

    @GetMapping("/role")
    public ResponseEntity<?> getUserRole(@RequestParam String email) {
        Optional<User> userOpt = userService.findByEmail(email);

        if (userOpt.isPresent()) {
            Role role = userOpt.get().getRole();
            return ResponseEntity.ok(role);
        }
        return ResponseEntity.badRequest().body("Error: User not found");
    }
}
