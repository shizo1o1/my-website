package com.example.mywebsite.controllers;

import com.example.mywebsite.models.Cart;
import com.example.mywebsite.models.CartItem;
import com.example.mywebsite.models.Product;
import com.example.mywebsite.repository.ProductRepository;
import com.example.mywebsite.services.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String addToCart(@RequestParam("productId") Long productId,
                            HttpSession session) {
        Product product = productRepository.findById(productId).orElse(null);
        cartService.addItemToCart(session, product);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam("productId") Long productId,
                                 HttpSession session) {
        Product product = productRepository.findById(productId).orElse(null);
        cartService.removeItemFromCart(session, product);
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateCartItem(@RequestParam("productId") Long productId,
                                 @RequestParam("quantity") int quantity,
                                 HttpSession session) {
        Product product = productRepository.findById(productId).orElse(null);
        cartService.updateItemQuantity(session, product, quantity);
        return "redirect:/cart";
    }

}

