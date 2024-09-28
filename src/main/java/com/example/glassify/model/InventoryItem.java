package com.example.glassify.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Inventory")
@Data
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "stockQuantity", nullable = false)
    private Integer stockQuantity;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @Column(name = "vendor")
    private String vendor;

    @Column(name = "contactPhoneNumber")
    private String contactPhoneNumber;

    @Column(name = "contactEmail")
    private String contactEmail;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}