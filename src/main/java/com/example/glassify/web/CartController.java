package com.example.glassify.web;

import com.example.glassify.model.CartItem;
import com.example.glassify.service.CartService;
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

    @PostMapping
    public ResponseEntity<Long> createCart() {
        try {
            Long cartId = cartService.createCart();
            return ResponseEntity.status(HttpStatus.CREATED).body(cartId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/{cartId}/add")
    public ResponseEntity<CartItem> addItemToCart(@PathVariable("cartId") Long cartId, @RequestBody CartItem cartItem) {
        try {
            CartItem createdCartItem = cartService.addItemToCart(cartId, cartItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCartItem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
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

