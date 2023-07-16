package com.example.mywebsite.controllers;

import com.example.mywebsite.models.Cart;
import com.example.mywebsite.models.Order;
import com.example.mywebsite.repository.OrderRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/order")
    public String createOrder(@RequestParam("userName") String userName,
                              @RequestParam("userPhone") String userPhone,
                              HttpSession session){

        Cart cart = (Cart) session.getAttribute("cart");

        Order order = new Order();
        order.setItems(cart.getItems());
        order.setUserName(userName);
        order.setPhone(userPhone);

        orderRepository.save(order);
        cart.clear();


        return "redirect:/";
    }
}
