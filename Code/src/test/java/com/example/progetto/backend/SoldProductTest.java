package com.example.progetto.backend;

import static org.junit.Assert.*;
import org.junit.Test;

public class SoldProductTest {

    @Test
    public void testSettersAndGetters() {
        SoldProduct sp = new SoldProduct();
        sp.setID(1);
        sp.setIDorder(100);
        sp.setModel("TestModel");
        sp.setSize("M");

        assertEquals(1, sp.getID());
        assertEquals(100, sp.getIDorder());
        assertEquals("TestModel", sp.getModel());
        assertEquals("M", sp.getSize());
    }
}
