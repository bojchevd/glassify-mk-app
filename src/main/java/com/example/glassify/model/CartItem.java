package com.example.glassify.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "CartItems")
@Data
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cartId", nullable = false)
    @JsonBackReference
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

    @Column(name = "albumName")
    private String albumName;

    @Column(name = "customDetails")
    private String customDetails;

    @Column(name = "photoUrl")
    private String photoUrl;

    @Column(name = "frameColor")
    private String frameColor;

    @Column(name = "price")
    private Integer price;

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", product=" + product +
                ", quantity=" + quantity +
                ", songName='" + songName + '\'' +
                ", artistName='" + artistName + '\'' +
                ", albumName='" + albumName + '\'' +
                ", customDetails='" + customDetails + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", frameColor='" + frameColor + '\'' +
                '}';
    }
}
