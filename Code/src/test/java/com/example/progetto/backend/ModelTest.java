package com.example.progetto.backend;

import static org.junit.Assert.*;
import org.junit.Test;

public class ModelTest {

    @Test
    public void testConstructorWithImageUrl() {
        Model model = new Model("TestModel", 99.99, "TestCategory", "TestDescription");
        assertEquals("TestModel", model.getName());
        assertEquals(99.99, model.getPrice(), 0.001);
        assertEquals("TestCategory", model.getCategory());
        assertEquals("TestDescription", model.getDescription());
        
    }

    @Test
    public void testConstructorWithId() {
        Model model = new Model(1, "ModelWithId", 50.0, "Category1", "A model with id");
        assertEquals(1, model.getId());
        assertEquals("ModelWithId", model.getName());
        assertEquals(50.0, model.getPrice(), 0.001);
        assertEquals("Category1", model.getCategory());
        assertEquals("A model with id", model.getDescription());
    }

    @Test
    public void testConstructorWithNameOnly() {
        Model model = new Model("OnlyName");
        assertEquals("OnlyName", model.getName());
    }

    @Test
    public void testDefaultConstructorAndSetters() {
        Model model = new Model();
        model.setId(10);
        model.setName("DefaultName");
        model.setPrice(10.0);
        model.setCategory("DefaultCategory");
        model.setDescription("Default description");
        
        assertEquals(10, model.getId());
        assertEquals("DefaultName", model.getName());
        assertEquals(10.0, model.getPrice(), 0.001);
        assertEquals("DefaultCategory", model.getCategory());
        assertEquals("Default description", model.getDescription());
       
    }
}
