package com.example.application.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.progetto.backend.Model;
import com.example.progetto.backend.Product;
import com.example.progetto.backend.User;
import com.vaadin.flow.component.html.Image;

import jakarta.validation.constraints.AssertFalse.List;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:databases/db.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public User findUserByEmail(String email) throws SQLException {
        String query = "SELECT * FROM User WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("ID"));
                    user.setName(rs.getString("name"));
                    user.setSurname(rs.getString("surname"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(rs.getString("role"));                 
                    return user;
                }
            }
        }
        return null;
    }

    public void saveUser(User user) throws SQLException {
        String query = "INSERT INTO User (name, surname, email, password, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getSurname());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getRole());
            stmt.executeUpdate();
        }
    }
    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE User SET name = ?, surname = ?, email = ?, password = ? WHERE id = ?";
        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getSurname());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPassword());
            pstmt.setInt(5, user.getId());
            pstmt.executeUpdate();
        }
    }
    public ResultSet returnAllUser() throws SQLException {
    	String sql = "SELECT * FROM User";
    	ResultSet rs=null;
    	try (Connection conn = this.getConnection();
    			PreparedStatement pstmt = conn.prepareStatement(sql)) {
    			rs = pstmt.executeQuery();
           }
    	return rs;
    }
    public ArrayList<Model> returnModelsOnCategory(String category) throws SQLException {
    	ArrayList<Model> models = new ArrayList<>();
    	String sql = "SELECT * FROM Model WHERE category = "+category;
    	ResultSet rs = null;
    	try (Connection conn = this.getConnection();
    			PreparedStatement pstmt = conn.prepareStatement(sql)) {
    			rs = pstmt.executeQuery();
           }
    	while (rs.next()) {
            int id = rs.getInt("ID");
            String name = rs.getString("name");
            String description = rs.getString("description");
            double price = rs.getDouble("price");
            Model model = new Model(id,name,price,category,description);
            models.add(model);
        }
    return models;	
    }
}
    


