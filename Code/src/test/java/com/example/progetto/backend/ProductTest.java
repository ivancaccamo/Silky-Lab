package com.example.progetto.backend;

import static org.junit.Assert.*;
import org.junit.Test;

public class ProductTest {

    @Test
    public void testParameterizedConstructor() {
        Model model = new Model("TestModel");
        Product product = new Product(10, "L", model);
        
        assertEquals(10, product.getId());
        assertEquals("L", product.getSize());
        assertEquals(model, product.getModel());
    }

    @Test
    public void testDefaultConstructorAndSetters() {
        Product product = new Product();
        Model model = new Model("DefaultModel");
        
        product.setId(5);
        product.setSize("M");
        product.setModel(model);
        
        assertEquals(5, product.getId());
        assertEquals("M", product.getSize());
        assertEquals(model, product.getModel());
    }
}
