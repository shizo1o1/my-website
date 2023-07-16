package com.example.mywebsite.repository;

import com.example.mywebsite.models.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
