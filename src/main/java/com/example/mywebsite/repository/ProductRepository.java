package com.example.mywebsite.repository;

import com.example.mywebsite.models.Category;
import com.example.mywebsite.models.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findByCategory(Category category);
}
