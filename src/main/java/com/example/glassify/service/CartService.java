package com.example.glassify.service;

import com.example.glassify.model.Cart;
import com.example.glassify.model.CartItem;
import com.example.glassify.repository.CartItemRepository;
import com.example.glassify.repository.CartRepository;
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
        // Check if the cart exists
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));

        // Set cart reference to cartItem
        cartItem.setCart(cart);

        // Save and return the cart item
        return cartItemRepository.save(cartItem);
    }

    public List<CartItem> getCartItems(Long cartId) {
        // Find the cart
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));

        // Return all cart items for the given cart
        return cartItemRepository.findByCart(cart);
    }

    public void removeItemFromCart(Long cartId, Long itemId) {
        // Find the cart and item
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        CartItem cartItem = cartItemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Cart item not found"));

        // Check if the cart item belongs to the cart
        if (!cartItem.getCart().equals(cart)) {
            throw new RuntimeException("Cart item does not belong to the specified cart");
        }

        // Remove the cart item
        cartItemRepository.delete(cartItem);
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

