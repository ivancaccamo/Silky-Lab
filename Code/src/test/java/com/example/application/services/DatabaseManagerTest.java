package com.example.application.services;

import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.example.progetto.backend.Address;
import com.example.progetto.backend.Model;
import com.example.progetto.backend.Order;
import com.example.progetto.backend.Product;
import com.example.progetto.backend.SoldProduct;
import com.example.progetto.backend.User;

public class DatabaseManagerTest {

    private static DatabaseManager dbManager = new DatabaseManager();

    private static int initialUserMax;
    private static int initialModelMax;
    private static int initialProductMax;
    private static int initialAddressMax;
    private static int initialSoldProductsMax;
    private static int initialOrderMax;

    @BeforeClass
    public static void setUpBeforeClass() throws SQLException {
        try (Connection conn = dbManager.getConnection()) {
            initialUserMax = getMaxId(conn, "User");
            initialModelMax = getMaxId(conn, "Model");
            initialProductMax = getMaxId(conn, "Product");
            initialAddressMax = getMaxId(conn, "Address");
            initialSoldProductsMax = getMaxId(conn, "SoldProducts");
            initialOrderMax = getMaxId(conn, "`Order`");
        }
    }

    private static int getMaxId(Connection conn, String table) throws SQLException {
        int max = 0;
        String sql = "SELECT MAX(ID) AS maxId FROM " + table;
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                max = rs.getInt("maxId");
            }
        }
        return max;
    }

    @AfterClass
    public static void tearDownAfterClass() throws SQLException {
        try (Connection conn = dbManager.getConnection()) {
            deleteNewRows(conn, "User", initialUserMax);
            deleteNewRows(conn, "Model", initialModelMax);
            deleteNewRows(conn, "Product", initialProductMax);
            deleteNewRows(conn, "Address", initialAddressMax);
            deleteNewRows(conn, "SoldProducts", initialSoldProductsMax);
            deleteNewRows(conn, "`Order`", initialOrderMax);
        }
    }

    private static void deleteNewRows(Connection conn, String table, int initialMax) throws SQLException {
        String sql = "DELETE FROM " + table + " WHERE ID > ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, initialMax);
            ps.executeUpdate();
        }
    }

    @Test
    public void testGetConnection() throws SQLException {
        Connection conn = dbManager.getConnection();
        assertNotNull(conn);
        conn.close();
    }

    @Test
    public void testSaveAndFindUser() throws SQLException {
        User user = new User();
        user.setName("john");
        user.setSurname("doe");
        user.setEmail("john@example.com");
        user.setPassword("pass");
        user.setRole("CLIENT");
        dbManager.saveUser(user);
        User foundByEmail = dbManager.findUserByEmail("john@example.com");
        assertNotNull(foundByEmail);
        assertEquals("John", foundByEmail.getName());
        User foundById = dbManager.findUserByID(foundByEmail.getId());
        assertNotNull(foundById);
        assertEquals("Doe", foundById.getSurname());
    }

    @Test
    public void testUpdateUser() throws SQLException {
        User user = new User();
        user.setName("alice");
        user.setSurname("smith");
        user.setEmail("alice@example.com");
        user.setPassword("1234");
        user.setRole("CLIENT");
        dbManager.saveUser(user);
        User saved = dbManager.findUserByEmail("alice@example.com");
        saved.setName("alicia");
        saved.setSurname("johnson");
        saved.setEmail("alicia@example.com");
        saved.setPassword("4321");
        dbManager.updateUser(saved);
        User updated = dbManager.findUserByID(saved.getId());
        assertEquals("Alicia", updated.getName());
        assertEquals("Johnson", updated.getSurname());
        assertEquals("alicia@example.com", updated.getEmail());
        assertEquals("4321", updated.getPassword());
    }

    @Test
    public void testReturnAllUser() throws SQLException {
        User user1 = new User();
        user1.setName("user1");
        user1.setSurname("one");
        user1.setEmail("user1@example.com");
        user1.setPassword("passTest1");
        user1.setRole("CLIENT");
        dbManager.saveUser(user1);
        User user2 = new User();
        user2.setName("userTest2");
        user2.setSurname("two");
        user2.setEmail("user2@example.com");
        user2.setPassword("pass2");
        user2.setRole("CLIENT");
        dbManager.saveUser(user2);
        ArrayList<User> users = dbManager.returnAllUser();
        assertTrue(users.size() > 1);
    }

    @Test
    public void testSaveAndReturnModel() throws SQLException {
        Model model = new Model("TestModel", 10.0, "CategoryA", "DescriptionA");
        dbManager.saveModel(model);
        Model returned = dbManager.returnModelByName("TestModel");
        assertNotNull(returned);
        assertEquals("TestModel", returned.getName());
        ArrayList<Model> models = dbManager.returnModelsOnCategory("CategoryA");
        boolean found = false;
        for (Model m : models) {
            if (m.getName().equals("TestModel")) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testReturnProductByModel() throws SQLException {
        Model model = new Model("ProdModel", 20.0, "CatB", "DescB");
        dbManager.saveModel(model);
        Model returnedModel = dbManager.returnModelByName("ProdModel");
        assertNotNull(returnedModel);
        int modelId = dbManager.returnIdModel(returnedModel);
        returnedModel.setId(modelId);
        dbManager.loadProducts(modelId);
        int count = dbManager.countRecords(returnedModel, "S");
        assertTrue(count > 0);
        Product product = dbManager.returnProductByModel(returnedModel, "S", 1);
        assertNotNull(product);
        assertEquals("S", product.getSize());
    }

    @Test
    public void testUpdateQuantity() throws SQLException {
        Model model = new Model("QtyModel", 15.0, "CatC", "DescC");
        dbManager.saveModel(model);
        Model returnedModel = dbManager.returnModelByName("QtyModel");
        int modelId = dbManager.returnIdModel(returnedModel);
        returnedModel.setId(modelId);
        dbManager.loadProducts(modelId);
        int current = dbManager.countRecords(returnedModel, "M");
        int newQty = current + 5;
        dbManager.updateQuantity(returnedModel, "M", newQty);
        int updatedCount = dbManager.countRecords(returnedModel, "M");
        assertEquals(newQty, updatedCount);
        newQty = updatedCount - 3;
        dbManager.updateQuantity(returnedModel, "M", newQty);
        int finalCount = dbManager.countRecords(returnedModel, "M");
        assertEquals(newQty, finalCount);
    }

    @Test
    public void testSaveAndGetAddresses() throws SQLException {
        User user = new User();
        user.setName("addr");
        user.setSurname("test");
        user.setEmail("addr@example.com");
        user.setPassword("passTest3");
        user.setRole("CLIENT");
        dbManager.saveUser(user);
        User saved = dbManager.findUserByEmail("addr@example.com");
        Address address = new Address();
        address.setAddress("main street");
        address.setCity("city");
        address.setCap("12345");
        address.setCountry("country");
        dbManager.saveAddress(address, saved.getId());
        ArrayList<Address> addresses = dbManager.getAddressesByUserId(saved.getId());
        assertTrue(addresses.size() > 0);
        Address addr = addresses.get(0);
        addr.setCity("newcity");
        dbManager.updateAddressById(addr);
        ArrayList<Address> updatedAddresses = dbManager.getAddressesByUserId(saved.getId());
        assertEquals("Newcity", updatedAddresses.get(0).getCity());
        dbManager.deleteAddressById(addr.getID());
        ArrayList<Address> afterDelete = dbManager.getAddressesByUserId(saved.getId());
        assertEquals(0, afterDelete.size());
    }

    @Test
    public void testSaveOrderAndGetOrders() throws SQLException {
        User user = new User();
        user.setName("order");
        user.setSurname("test");
        user.setEmail("order@example.com");
        user.setPassword("passTest4");
        user.setRole("CLIENT");
        dbManager.saveUser(user);
        User saved = dbManager.findUserByEmail("order@example.com");
        ArrayList<SoldProduct> soldProducts = new ArrayList<>();
        SoldProduct sp = new SoldProduct();
        sp.setModel("ModelX");
        sp.setSize("L");
        soldProducts.add(sp);
        dbManager.saveOrder(saved.getId(), 100.0, soldProducts);
        ArrayList<Order> orders = dbManager.getOrdersByUserId(saved.getId());
        assertTrue(orders.size() > 0);
        Order order = orders.get(0);
        ArrayList<SoldProduct> sold = dbManager.getSoldProductsByOrderId(order.getID());
        assertTrue(sold.size() > 0);
        ArrayList<Order> allOrders = dbManager.getOrders();
        assertTrue(allOrders.size() > 0);
    }

    @Test
    public void testDeleteModelByName() throws SQLException {
        Model model = new Model("DeleteModel", 30.0, "CatD", "DescD");
        dbManager.saveModel(model);
        Model returned = dbManager.returnModelByName("DeleteModel");
        assertNotNull(returned);
        dbManager.deleteModelByName("DeleteModel");
        Model deleted = dbManager.returnModelByName("DeleteModel");
        assertNull(deleted);
    }

    @Test
    public void testDeleteProductsByIdAndQuantity() throws SQLException {
        Model model = new Model("DelProdModel", 40.0, "CatE", "DescE");
        dbManager.saveModel(model);
        Model returned = dbManager.returnModelByName("DelProdModel");
        int modelId = dbManager.returnIdModel(returned);
        returned.setId(modelId);
        dbManager.loadProducts(modelId);
        int countBefore = dbManager.countRecords(returned, "XL");
        if(countBefore > 0) {
            dbManager.deleteProductsByIdAndQuantity(1, 5);
            int countAfter = dbManager.countRecords(returned, "XL");
            assertTrue(countAfter <= countBefore);
        }
    }

    @Test
    public void testCapitalizeAddress() {
        String result = dbManager.capitalizeAddress("main street");
        assertEquals("Main Street", result);
    }
}
