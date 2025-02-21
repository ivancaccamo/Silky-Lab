package com.example.progetto.backend;

import static org.junit.Assert.*;
import org.junit.Test;

public class UserTest {

    @Test
    public void testSettersAndGetters() {
        User user = new User();
        
        user.setId(1);
        assertEquals(1, user.getId());
        
        user.setName("John");
        assertEquals("John", user.getName());
        
        user.setSurname("Red");
        assertEquals("Red", user.getSurname());
        
        user.setEmail("john.red@example.com");
        assertEquals("john.red@example.com", user.getEmail());
        
        user.setPassword("secret");
        assertEquals("secret", user.getPassword());
        
        user.setRole("admin");
        assertEquals("admin", user.getRole());
    }
}