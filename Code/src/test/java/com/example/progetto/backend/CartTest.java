package com.example.progetto.backend;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class CartTest {

    @Before
    public void setUp() {
        Cart.setCartItems(new ArrayList<CartItem>());
    }

    @Test
    public void testAddItemAndGetCartItems() {
        Model model = new Model("TestModel");
        Product product = new Product(1, "M", model);
        Cart.addItem(product, 2);
        ArrayList<CartItem> items = Cart.getCartItems();
        assertNotNull(items);
        assertEquals(1, items.size());
        assertEquals(2, items.get(0).getQuantity());
        assertEquals(product, items.get(0).getProduct());
    }

    @Test
    public void testAddItemMerging() {
        Model model = new Model("MergeModel");
        Product product = new Product(2, "L", model);
        Cart.addItem(product, 3);
        Cart.addItem(product, 2);
        ArrayList<CartItem> items = Cart.getCartItems();
        assertEquals(1, items.size());
        assertEquals(5, items.get(0).getQuantity());
    }

    @Test
    public void testGetCartItemByModel() {
        Model model = new Model("ModelName");
        Product product = new Product(3, "S", model);
        Cart.addItem(product, 4);
        int quantity = Cart.getCartItemByModel(model, "S");
        assertEquals(4, quantity);
        int quantityNotFound = Cart.getCartItemByModel(new Model("NonExistent"), "S");
        assertEquals(0, quantityNotFound);
    }

    @Test
    public void testRemoveItem() {
        Model model = new Model("RemoveModel");
        Product product = new Product(4, "XL", model);
        Cart.addItem(product, 1);
        ArrayList<CartItem> items = Cart.getCartItems();
        CartItem item = items.get(0);
        Cart.removeItem(item);
        assertTrue(Cart.getCartItems().isEmpty());
    }

    @Test
    public void testRemoveAll() {
        Model model = new Model("RemoveAllModel");
        Product product1 = new Product(5, "M", model);
        Product product2 = new Product(6, "L", model);
        Cart.addItem(product1, 2);
        Cart.addItem(product2, 3);
        Cart.removeAll();
        assertTrue(Cart.getCartItems().isEmpty());
    }

    @Test
    public void testRemoveCartItemByIndex() {
        Model model = new Model("IndexModel");
        Product product1 = new Product(7, "S", model);
        Product product2 = new Product(8, "M", model);
        Cart.addItem(product1, 1);
        Cart.addItem(product2, 2);
        Cart cart = new Cart();
        cart.removeCartItem(0);
        ArrayList<CartItem> items = Cart.getCartItems();
        assertEquals(1, items.size());
        assertEquals(product2, items.get(0).getProduct());
    }

    @Test
    public void testSetCartItems() {
        ArrayList<CartItem> newItems = new ArrayList<>();
        Model model = new Model("SetModel");
        Product product = new Product(9, "L", model);
        newItems.add(new CartItem(product, 5));
        Cart.setCartItems(newItems);
        ArrayList<CartItem> items = Cart.getCartItems();
        assertEquals(1, items.size());
        assertEquals(5, items.get(0).getQuantity());
    }

    @Test
    public void testEmptyCart() {
        Model model = new Model("EmptyModel");
        Product product = new Product(10, "XL", model);
        Cart.addItem(product, 2);
        Cart.emptyCart();
        assertNull(Cart.getCartItems());
    }
}
