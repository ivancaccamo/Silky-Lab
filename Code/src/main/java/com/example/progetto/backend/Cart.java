package com.example.progetto.backend;

import java.util.ArrayList;
import java.util.List;

public class Cart {
	private static List<CartItem> items = new ArrayList<>();
	
	public static List<CartItem> getCartItems() {
		return items;
	}
	public static int getCartItemByProduct(Model model,String size) {
		
		for(CartItem item : items) {
			if(item.getProduct()==) {
				return item.getQuantity();
			}
		}
		return 0;
	}
	public static void setCartItems(List<CartItem> cartItems) {
		items = cartItems;
	}
	
	public void removeCartItem(int id) {
		items.remove(id);
	}
	public static void emptyCart() {
			items = null;
	}

	 public static void addItem(Product product, int quantity) {
	        for (CartItem item : items) {
	            if (item.getProduct().getId()==(product.getId()) && item.getProduct().getSize().equals(product.getSize())) 
	            {
	                item.setQuantity(item.getQuantity() + quantity);
	                return;
	            }
	        }
	        items.add(new CartItem(product, quantity));
	    }
}
