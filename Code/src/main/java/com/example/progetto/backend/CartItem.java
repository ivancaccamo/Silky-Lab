package com.example.progetto.backend;

public class CartItem {
    private Product product; // Riferimento al prodotto
    private int quantity;    // Quantità del prodotto nel carrello
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    // Getter e Setter
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
	
}

