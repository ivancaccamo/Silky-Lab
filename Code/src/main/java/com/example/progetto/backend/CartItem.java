package com.example.ecommerce.model;

public class CartItem {
    private Product product;
    private String size;
    private int quantity;

    public CartItem(Product product, String size, int quantity) {
        this.product = product;
        this.size = size;
        this.quantity = quantity;
    }

    // ... get/set
}
