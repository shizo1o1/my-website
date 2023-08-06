package com.example.mywebsite.services;

import com.example.mywebsite.models.Cart;
import com.example.mywebsite.models.CartItem;
import com.example.mywebsite.models.Order;
import com.example.mywebsite.repository.OrderRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    private final TelegramService telegramService;
    @Autowired
    public OrderService(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    public void saveOrderInDB (String userName,String userPhone, HttpSession session){
        Cart cart = (Cart) session.getAttribute("cart");

        Order order = new Order();
        order.setItems(cart.getItems());
        order.setUserName(userName);
        order.setPhone(userPhone);

        orderRepository.save(order);

        telegramService.sendMessage(cart,userName,userPhone);

        cart.clear();
    }
}
