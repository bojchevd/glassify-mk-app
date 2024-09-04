package com.example.glassify.web;

import com.example.glassify.model.User;
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
        if (userService.existsByUsername(userDto.getEmail()) || userService.existsByEmail(userDto.getEmail())) { // todo : check
            return ResponseEntity.badRequest().body("Error: Username or Email is already taken!");
        }
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.ROLE_CUSTOMER);
        if (!utilityService.isNullOrEmpty(userDto.getLastName())) {
            user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        }
        user.setCity(userDto.getCity());
        user.setShippingAddress(userDto.getShippingAddress());
        user.setPhoneNumber(userDto.getPhoneNumber());

        userService.saveUser(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String usernameOrEmail, @RequestParam String password) {
        Optional<User> userOpt = userService.findByEmailOrUsername(usernameOrEmail);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                String token = jwtUtil.generateToken(usernameOrEmail);
                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.badRequest().body("Error: Invalid credentials");
            }
        }
        return ResponseEntity.badRequest().body("Error: User not found");
    }

    @GetMapping("/role")
    public ResponseEntity<?> getUserRole(@RequestParam String username) {
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isPresent()) {
            Role role = userOpt.get().getRole();
            return ResponseEntity.ok(role);
        }
        return ResponseEntity.badRequest().body("Error: User not found");
    }
}
