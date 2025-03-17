package com.example.progetto.backend;

import java.util.ArrayList;
/**
 * Gestisce il carrello degli acquisti dell'utente.
 * <p>
 * Questa classe fornisce metodi per aggiungere, rimuovere e gestire gli
 * elementi nel carrello, mantenendo una lista statica di oggetti {@link CartItem}.
 * </p>
 */
public class Cart {
	private static ArrayList<CartItem> items = new ArrayList<>();
	
	public static ArrayList<CartItem> getCartItems() {
		return items;
	}
	/**
     * Restituisce la quantità di un prodotto nel carrello in base al modello e alla taglia.
     *
     * @param model il modello del prodotto
     * @param size  la taglia del prodotto
     * @return la quantità del prodotto nel carrello, oppure 0 se non presente
     */
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
	/**
     * Rimuove un elemento dal carrello in base all'indice specificato.
     *
     * @param id l'indice dell'elemento da rimuovere
     */
	public void removeCartItem(int id) { 
		items.remove(id);
	}
	/**
     * Svuota completamente il carrello.
     */
	public static void emptyCart() {
		items = null;
	}
	/**
     * Aggiunge un prodotto al carrello.
     * <p>
     * Se il prodotto con la stessa taglia è già presente, la quantità viene aggiornata.
     * Altrimenti, il prodotto viene aggiunto come nuovo elemento nel carrello.
     * </p>
     *
     * @param product  il prodotto da aggiungere
     * @param quantity la quantità da aggiungere
     */
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
