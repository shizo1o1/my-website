package com.example.mywebsite.controllers;

import com.example.mywebsite.models.Category;
import com.example.mywebsite.models.Product;
import com.example.mywebsite.repository.CategoryRepository;
import com.example.mywebsite.repository.ProductRepository;
import com.example.mywebsite.services.DownloadPriceService;
import com.example.mywebsite.services.ReadPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private DownloadPriceService priceService;
    @Autowired
    private ReadPriceService readPriceService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
        public  String showMainPage(Model model) throws IOException {
//            readPriceService.saveInDbCategoryProduct();
            Iterable<Category> categories = categoryRepository.findAll();
            model.addAttribute("categories", categories);
            return "index";
        }

    @GetMapping("/category/{categoryId}/products")
        public String showProductByCategory(@PathVariable Long categoryId, Model model){
            Category category = categoryRepository.findById(categoryId).orElse(null);
            if (category != null){
                List<Product> products = productRepository.findByCategory(category);
                model.addAttribute("products", products);
                return "products-by-category";
            }
            return "error";
    }

}