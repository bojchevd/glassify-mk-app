package com.example.glassify.web;

import com.example.glassify.model.Cart;
import com.example.glassify.model.CartItem;
import com.example.glassify.model.Product;
import com.example.glassify.model.dto.CartItemDTO;
import com.example.glassify.model.dto.OrderDetailsDTO;
import com.example.glassify.service.CartService;
import com.example.glassify.service.ProductService;
import org.checkerframework.checker.units.qual.C;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @PostMapping("/create")
    public ResponseEntity<String> createCart() {
        try {
            Long cartId = cartService.createCart();
            logger.info("Cart created with id: " + cartId);
            return ResponseEntity.status(HttpStatus.CREATED).body(String.valueOf(cartId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/{cartId}/add")
    public ResponseEntity<CartItem> addItemToCart(@PathVariable("cartId") String cartId, @RequestBody CartItemDTO cartItemDTO) {
        try {
            Cart cart = cartService.getCartById(Long.valueOf(cartId));
            Product product = productService.getProductByName(cartItemDTO.getProductName());
            CartItem cartItem = setCartItem(cartItemDTO, cart, product);
            cartService.addItemToCart(Long.valueOf(cartId), cartItem);
            logger.info("Cart item added for product: " + cartItemDTO.getProductName());
            return ResponseEntity.status(HttpStatus.CREATED).body(cartItem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    private static CartItem setCartItem(CartItemDTO cartItemDTO, Cart cart, Product product) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setSongName(cartItemDTO.getSongName());
        cartItem.setArtistName(cartItemDTO.getArtistName());
        cartItem.setCustomDetails(cartItemDTO.getCustomDetails());
        cartItem.setPhotoUrl(cartItemDTO.getPhotoUrl());
        cartItem.setFrameColor(cartItemDTO.getFrameColor());
        return cartItem;
    }

    @GetMapping("/{cartId}/getAll")
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable("cartId") Long cartId) {
        List<CartItem> cartItems = cartService.getCartItems(cartId);
        return ResponseEntity.ok(cartItems);
    }

    @DeleteMapping("/{cartId}/remove/{itemId}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable("cartId") Long cartId, @PathVariable("itemId") Long itemId) {
        try {
            cartService.removeItemFromCart(cartId, itemId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<Void> removeAllCartItems(@PathVariable("cartId") Long cartId) {
        try {
            cartService.clearCart(cartId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

