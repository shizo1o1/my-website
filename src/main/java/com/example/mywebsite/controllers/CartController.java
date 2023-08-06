package com.example.mywebsite.controllers;

import com.example.mywebsite.models.Cart;
import com.example.mywebsite.models.Product;
import com.example.mywebsite.repository.ProductRepository;
import com.example.mywebsite.services.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("")
    public String viewCart(Model model, HttpSession session) {
        Cart cart = cartService.getCart(session);
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestParam("productId") Long productId,
                                            HttpSession session) {
        Product product = productRepository.findById(productId).orElse(null);
        cartService.addItemToCart(session, product);

        return ResponseEntity.ok().build();
    }


    @PostMapping("/remove")
    public String removeFromCart(@RequestParam("productId") Long productId,
                                 HttpSession session) {
        Product product = productRepository.findById(productId).orElse(null);
        cartService.removeItemFromCart(session, product);
        return "redirect:/cart";
    }

    @PostMapping(value = "/update", produces = "text/plain")
    @ResponseBody
    public String updateCartItem(@RequestParam("productId") Long productId,
                                 @RequestParam("quantity") int quantity,
                                 HttpSession session) {
        Product product = productRepository.findById(productId).orElse(null);
        cartService.updateItemQuantity(session, product, quantity);
        return "Success";
    }


    @GetMapping("/api/cart/count")
    @ResponseBody
    public int getCartItemCount(HttpSession session) {
        return cartService.getCartItemCount(session);
    }



}

