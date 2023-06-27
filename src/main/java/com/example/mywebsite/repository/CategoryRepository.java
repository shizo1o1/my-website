package com.example.mywebsite.repository;

import com.example.mywebsite.models.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {

}
