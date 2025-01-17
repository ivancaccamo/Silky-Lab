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
import com.vaadin.flow.component.notification.Notification;

import jakarta.validation.constraints.AssertFalse.List;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:databases/db.db";

    public Connection getConnection() throws SQLException {
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
    	String sql = "SELECT * FROM Model WHERE category = '"+category+"'";
    	ResultSet rs = null;
    	try (Connection conn = this.getConnection();
    			PreparedStatement pstmt = conn.prepareStatement(sql)) {
    			rs = pstmt.executeQuery();
    			while (rs.next()) {
    			
    				int id = rs.getInt("ID");
    				String name = rs.getString("name");
    				String description = rs.getString("description");
    				double price = rs.getDouble("price");
    				Model model = new Model(id,name,price,category,description);
    				models.add(model);  	
           }

    
        }
    return models;	
    }

public void saveModel(Model model) throws SQLException {
    String query = "INSERT INTO Model (name, price, category, description) VALUES (?, ?, ?, ?)";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, model.getName());
        stmt.setDouble(2, model.getPrice());
        stmt.setString(3, model.getCategory());
        stmt.setString(4, model.getDescription());
        stmt.executeUpdate();
    }
}
public Model returnModelByName(String name) throws SQLException {
	
	String sql = "SELECT * FROM Model WHERE name = '"+name+"'";
	ResultSet rs = null;
	try (Connection conn = this.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			rs = pstmt.executeQuery();
				int id = rs.getInt("ID");
				String category = rs.getString("category");
				String description = rs.getString("description");
				double price = rs.getDouble("price");
				Model model = new Model(id,name,price,category,description);
				return model;
    }
}
public Product returnProductByModel(Model model,String size,int qnt) throws SQLException {
	String sql = "SELECT *\r\n"
			+ "FROM (\r\n"
			+ "    SELECT *\r\n"
			+ "    FROM Product\r\n"
			+ "    WHERE modelID = '?' AND size = '?'\r\n"
			+ "    LIMIT ?\r\n"
			+ ") Subquery\r\n"
			+ "WHERE (SELECT COUNT(*)\r\n"
			+ "       FROM product\r\n"
			+ "       WHERE modelID = '?' AND size = '?') = ?;";
	ResultSet rs = null;
	try (Connection conn = this.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setInt(1, model.getId());	
				pstmt.setString(2, size);
				pstmt.setInt(3, qnt);
				pstmt.setInt(4, model.getId());
				pstmt.setString(5, size);
				pstmt.setInt(6, qnt);
				rs = pstmt.executeQuery();
				if(rs==null) {
					return null;
				}else {
					int id = rs.getInt("ID");
					String description = rs.getString("description");
					double price = rs.getDouble("price");
					Product product = new Product(id,size,model);
					return product;
				}
				
    }
}
	
}
 


