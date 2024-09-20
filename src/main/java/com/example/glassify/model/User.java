package com.example.glassify.model;

import com.example.glassify.model.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String name;

    private String shippingAddress;

    private String city;
    private String phoneNumber;

    private boolean enabled = true;

}