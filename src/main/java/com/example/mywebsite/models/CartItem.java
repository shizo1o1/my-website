package com.example.mywebsite.models;

import jakarta.persistence.*;

@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    private String productName;  // New field to store the product name

    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.productName = product.getName();  // Set the product name during construction
        this.quantity = quantity;
    }

    public CartItem() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        this.productName = product.getName();  // Update the product name when setting the product
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
