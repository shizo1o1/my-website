package com.example.mywebsite.repository;

import com.example.mywebsite.models.Product;
import org.springframework.data.repository.CrudRepository;
public interface ProductRepository extends CrudRepository<Product, Long> {
}
