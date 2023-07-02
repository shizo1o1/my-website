package com.example.mywebsite.services;

import com.example.mywebsite.models.Cart;
import com.example.mywebsite.models.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private static final String CART_SESSION_KEY = "cart";

    public void addItemToCart(HttpSession session, Product product) {
        Cart cart = getCart(session);
        cart.addItem(product);
        saveCart(session, cart);
    }

    public void removeItemFromCart(HttpSession session, Product product) {
        Cart cart = getCart(session);
        cart.removeItem(product);
        saveCart(session, cart);
    }

    public void updateItemQuantity(HttpSession session, Product product, int quantity) {
        Cart cart = getCart(session);
        cart.updateItemQuantity(product, quantity);
        saveCart(session, cart);
    }

    public Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new Cart();
            saveCart(session, cart);
        }
        return cart;
    }

    private void saveCart(HttpSession session, Cart cart) {
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public int getCartItemCount(HttpSession session) {
        Cart cart = getCart(session);
        return cart.getItems().size();
    }
}
