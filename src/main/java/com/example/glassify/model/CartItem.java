package com.example.glassify.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Entity
@Table(name = "CartItems")
@Data
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cartId", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "songName")
    private String songName;

    @Column(name = "artistName")
    private String artistName;

    @Column(name = "customDetails")
    private String customDetails;

    @Column(name = "photoUrl")
    private String photoUrl;

    @Column(name = "frameColor")
    private String frameColor;

}