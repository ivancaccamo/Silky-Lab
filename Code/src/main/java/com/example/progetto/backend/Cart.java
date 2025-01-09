package com.example.progetto.backend;

import java.util.ArrayList;
import java.util.List;

public class Cart {
	private static List<Product> Products = new ArrayList<>();
	
	public static List<Product> getCartItems() {
		return Products;
	}
	public static void setCartItems(List<Product> cartItems) {
		Products = cartItems;
	}
	public void addCartItem(Product item) {
		Products.add(item);
	}
	public static void emptyCart() {
			Products = null;
	}
}
