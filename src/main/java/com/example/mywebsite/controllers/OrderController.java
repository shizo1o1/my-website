package com.example.mywebsite.controllers;

import com.example.mywebsite.repository.OrderRepository;
import com.example.mywebsite.services.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/order")
    public String createOrder(@RequestParam("userName") String userName,
                              @RequestParam("userPhone") String userPhone,
                              HttpSession session){

        orderService.saveOrderInDB(userName,userPhone, session);

        return "redirect:/";
    }
}
