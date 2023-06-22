package com.example.mywebsite.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int price, alliance_id, guarantee;
    private String product_name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAlliance_id() {
        return alliance_id;
    }

    public void setAlliance_id(int alliance_id) {
        this.alliance_id = alliance_id;
    }

    public int getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(int guarantee) {
        this.guarantee = guarantee;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public Product() {
    }

    public Product(int price, int alliance_id, int guarantee, String product_name) {
        this.price = price;
        this.alliance_id = alliance_id;
        this.guarantee = guarantee;
        this.product_name = product_name;
    }
}
