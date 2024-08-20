package com.example.glassify.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "OrderItems")
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orderId", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "customDetails")
    private String customDetails;

    @Column(name = "photoUrl")
    private String photoUrl;

    @Column(name = "frameColor")
    private String frameColor;

    @Column(name = "templateType")
    private String templateType;

    @Column(name = "songName")
    private String songName;

    @Column(name = "artistName")
    private String artistName;

    @Column(name = "posterName")
    private String posterName;
}