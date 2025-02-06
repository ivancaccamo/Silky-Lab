package com.example.progetto.backend;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class CartTest {

    @BeforeEach
    public void setUp() {
        Cart.setCartItems(new ArrayList<>());
    }

    @Test
    public void testAddItem() {
        Model model = new Model("TestModel");
        Product product = new Product(1, "M", model);
        Cart.addItem(product, 2);
        assertEquals(2, Cart.getCartItemByModel(model, "M"));
    }

    @Test
    public void testAddSameItem() {
        Model model = new Model("TestModel");
        Product product = new Product(1, "L", model);
        Cart.addItem(product, 1);
        Cart.addItem(product, 3);
        assertEquals(4, Cart.getCartItemByModel(model, "L"));
    }

    @Test
    public void testRemoveItem() {
        Model model = new Model("TestModel");
        Product product = new Product(1, "S", model);
        Cart.addItem(product, 2);
        ArrayList<CartItem> items = Cart.getCartItems();
        assertFalse(items.isEmpty());
        Cart.removeItem(items.get(0));
        assertEquals(0, Cart.getCartItemByModel(model, "S"));
    }

    @Test
    public void testRemoveCartItemByIndex() {
        Model model = new Model("TestModel");
        Product product = new Product(1, "M", model);
        Cart.addItem(product, 2);
        new Cart().removeCartItem(0);
        assertEquals(0, Cart.getCartItemByModel(model, "M"));
    }

    @Test
    public void testRemoveAll() {
        Model model1 = new Model("TestModel1");
        Model model2 = new Model("TestModel2");
        Product product1 = new Product(1, "S", model1);
        Product product2 = new Product(2, "M", model2);
        Cart.addItem(product1, 2);
        Cart.addItem(product2, 3);
        assertFalse(Cart.getCartItems().isEmpty());
        Cart.removeAll();
        assertTrue(Cart.getCartItems().isEmpty());
    }

    @Test
    public void testEmptyCart() {
        Model model = new Model("TestModel");
        Product product = new Product(1, "XL", model);
        Cart.addItem(product, 2);
        Cart.emptyCart();
        assertNull(Cart.getCartItems());
    }
}
