package com.example.progetto.backend;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class OrderTest {

    @Test
    public void testConstructorAndGetters() throws SQLException {
        Date date = Date.valueOf("2020-01-01");
        Order order = new Order(1, 100, date, 200.50);
        assertEquals(1, order.getID());
        assertEquals(100, order.getIDuser());
        assertEquals(date, order.getDate());
        assertEquals(200.50, order.getTot(), 0.001);
        assertNotNull(order.getItems());
    }

    @Test
    public void testSetters() throws SQLException {
        Date date = Date.valueOf("2020-01-01");
        Order order = new Order(2, 200, date, 300.75);
        order.setID(3);
        order.setIDuser(250);
        Date newDate = Date.valueOf("2020-02-01");
        order.setDate(newDate);
        order.setTot(400.00);
        ArrayList<SoldProduct> newItems = new ArrayList<>();
        order.setItems(newItems);
        assertEquals(3, order.getID());
        assertEquals(250, order.getIDuser());
        assertEquals(newDate, order.getDate());
        assertEquals(400.00, order.getTot(), 0.001);
        assertEquals(newItems, order.getItems());
    }
}
