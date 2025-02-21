package com.example.progetto.backend;

import static org.junit.Assert.*;
import org.junit.Test;

public class CartItemTest {

    private class DummyProduct extends Product {
        private int id;
        public DummyProduct(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            DummyProduct other = (DummyProduct) obj;
            return id == other.id;
        }
    }

    @Test
    public void testConstructorAndGetters() {
        DummyProduct product = new DummyProduct(1);
        CartItem item = new CartItem(product, 2);
        assertEquals(product, item.getProduct());
        assertEquals(2, item.getQuantity());
    }

    @Test
    public void testSetProduct() {
        DummyProduct product1 = new DummyProduct(1);
        DummyProduct product2 = new DummyProduct(2);
        CartItem item = new CartItem(product1, 3);
        item.setProduct(product2);
        assertEquals(product2, item.getProduct());
    }

    @Test
    public void testSetQuantity() {
        DummyProduct product = new DummyProduct(1);
        CartItem item = new CartItem(product, 3);
        item.setQuantity(5);
        assertEquals(5, item.getQuantity());
    }
}
