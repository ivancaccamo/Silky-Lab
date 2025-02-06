package com.example.progetto.backend;

import java.util.ArrayList;

public class Cart {
	private static ArrayList<CartItem> items = new ArrayList<>();
	
	public static ArrayList<CartItem> getCartItems() {
		return items;
	}
	public static int getCartItemByModel(Model model,String size) {
		
		for(CartItem item : items) {
			
			if(item.getProduct().getModel().getName().equals(model.getName())&&item.getProduct().getSize().equals(size)) {
				return item.getQuantity();
			}
		}
		return 0;
	}
	public static void setCartItems(ArrayList<CartItem> cartItems) {
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
	 
	 public static void removeItem(CartItem item) {
		 items.removeIf(existingItem -> existingItem.equals(item));
	}
	 
	 public static void removeAll() {
		 items.removeAll(items);
	}
}
