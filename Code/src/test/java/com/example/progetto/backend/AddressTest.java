package com.example.progetto.backend;

import static org.junit.Assert.*;
import org.junit.Test;

public class AddressTest {

    @Test
    public void testDefaultConstructorAndSetters() {
        Address addr = new Address();
        addr.setID(1);
        addr.setAddress("123 Test St");
        addr.setCity("TestCity");
        addr.setCap("12345");
        addr.setCountry("TestCountry");
        addr.setIDuser(2);

        assertEquals(1, addr.getID());
        assertEquals("123 Test St", addr.getAddress());
        assertEquals("TestCity", addr.getCity());
        assertEquals("12345", addr.getCap());
        assertEquals("TestCountry", addr.getCountry());
        assertEquals(2, addr.getIDuser());
    }

    @Test
    public void testParameterizedConstructor() {
        Address addr = new Address(10, "456 Main Ave", "MainCity", "54321", "MainCountry", 20);

        assertEquals(10, addr.getID());
        assertEquals("456 Main Ave", addr.getAddress());
        assertEquals("MainCity", addr.getCity());
        assertEquals("54321", addr.getCap());
        assertEquals("MainCountry", addr.getCountry());
        assertEquals(20, addr.getIDuser());
    }

    @Test
    public void testSettersAndGettersIndependently() {
        Address addr = new Address();
        addr.setID(5);
        addr.setAddress("789 Side Rd");
        addr.setCity("SideCity");
        addr.setCap("67890");
        addr.setCountry("SideCountry");
        addr.setIDuser(30);

        assertEquals(5, addr.getID());
        assertEquals("789 Side Rd", addr.getAddress());
        assertEquals("SideCity", addr.getCity());
        assertEquals("67890", addr.getCap());
        assertEquals("SideCountry", addr.getCountry());
        assertEquals(30, addr.getIDuser());
    }
}
