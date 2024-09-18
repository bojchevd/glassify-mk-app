package com.example.glassify.service;

import com.example.glassify.model.Cart;
import com.example.glassify.model.CartItem;
import com.example.glassify.repository.CartItemRepository;
import com.example.glassify.repository.CartRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart Not Found!"));
    }

    public Long createCart() {
        Cart cart = new Cart();
        // Initialize any required fields, e.g., user information if applicable
        // cart.setUser(user); // Example if you have user information
        Cart createdCart = cartRepository.save(cart);
        return createdCart.getId(); // Return the ID of the newly created cart
    }

    public CartItem addItemToCart(Long cartId, CartItem cartItem) {

        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        cartItem.setCart(cart);
        return cartItemRepository.save(cartItem);
    }

    public List<CartItem> getCartItems(Long cartId) {
        // Find the cart
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));

        // Return all cart items for the given cart
        return cartItemRepository.findByCart(cart);
    }

    public void removeItemFromCart(Long cartId, Long itemId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        CartItem itemToRemove = cart.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Item not found in cart"));

        // Check if the cart item belongs to the cart
        if (!itemToRemove.getCart().equals(cart)) {
            throw new RuntimeException("Cart item does not belong to the specified cart");
        }

        cart.getItems().remove(itemToRemove);
        cartItemRepository.delete(itemToRemove);
    }

    public void clearCart(Long cartId) {
        // Find the cart
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));

        // Find all cart items for the given cart
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);

        // Remove all cart items
        cartItemRepository.deleteAll(cartItems);
    }
}

