package com.example.progetto.backend;

import static org.junit.Assert.*;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class CurrentTest {

    @Before
    public void setUp() {
        Current.setTheInstance(null);
        Current.setCurrentUser(null);
        Current.setProductView(null);
        Current.setCategoryView(null);
        Cart.setCartItems(new ArrayList<CartItem>());
    }

    @Test
    public void testCurrentUserSetGet() {
        User user = new User();
        user.setId(1);
        user.setName("Alice");
        Current.setCurrentUser(user);
        assertEquals(user, Current.getCurrentUser());
    }

    @Test
    public void testProductViewSetGet() {
        String productView = "TestProductView";
        Current.setProductView(productView);
        assertEquals(productView, Current.getProductView());
    }

    @Test
    public void testCategoryViewSetGet() {
        String categoryView = "TestCategoryView";
        Current.setCategoryView(categoryView);
        assertEquals(categoryView, Current.getCategoryView());
    }

    @Test
    public void testSetUserCreatesInstance() {
        User user = new User();
        user.setId(2);
        user.setName("Bob");
        Current.setUser(user);
        assertNotNull(Current.getTheInstance());
        assertEquals(user, Current.getUser());
    }

    @Test
    public void testExitUser() {
        User user = new User();
        user.setId(3);
        user.setName("Charlie");
        Current.setUser(user);
        Model model = new Model("TestModel");
        Product product = new Product(1, "M", model);
        Cart.addItem(product, 5);
        assertFalse(Cart.getCartItems().isEmpty());
        Current.ExitUser();
        assertNull(Current.getCurrentUser());
        assertTrue(Cart.getCartItems().isEmpty());
    }

    @Test
    public void testSetTheInstance() {
        Current instance = null;
        try {
            Constructor<Current> cons = Current.class.getDeclaredConstructor();
            cons.setAccessible(true);
            instance = cons.newInstance();
        } catch (Exception e) {
            fail("Failed to create instance");
        }
        Current.setTheInstance(instance);
        assertEquals(instance, Current.getTheInstance());
    }
}
