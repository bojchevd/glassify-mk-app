package com.example.glassify.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "basePrice", nullable = false)
    private Integer basePrice;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "description")
    private String description;

}