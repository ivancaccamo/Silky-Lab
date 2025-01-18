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
public Product returnProductByModel(Model model, String size, int qnt) throws SQLException {
    String sql = "SELECT * FROM Product WHERE modelID = ? AND size = ? LIMIT 1;";
    ResultSet rs = null;

    try (Connection conn = this.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, model.getId());
        pstmt.setString(2, size);
        rs = pstmt.executeQuery();

        if (!rs.next()) {
            System.out.println("ResultSet è vuoto");
            int id = rs.getInt("ID");
            System.out.println("ID: " + id);
            return null;
        }

        // Se il ResultSet contiene dati
        int id = rs.getInt("ID");
        // Creazione del prodotto
        Product product = new Product(id, size, model);
        System.out.println("ID: " + id);
        System.out.println("ID: " + model.getId());
         
        return product;
    }
}

	

	public int countRecords(Model model,String size) throws SQLException {
		int count = 0;
		String sql = "SELECT COUNT(*) AS TotalRecord FROM Product WHERE modelID = ? AND size = ?;";
		ResultSet rs = null;
		try (Connection conn = this.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
					pstmt.setInt(1, model.getId());	
					pstmt.setString(2, size);
					rs = pstmt.executeQuery();		
					if(!rs.isBeforeFirst()) {
						count = 0;
					}else {
						count = rs.getInt("TotalRecords");	
					}				
					}
	return count;
	}
public void loadProducts(int idModel) throws SQLException {
	String sql = "INSERT INTO Product (modelID, size)\r\n"
			+ "VALUES \r\n"
			+ "-- 15 righe per Small\r\n"
			+ "(?, 'Small'),\r\n"
			+ "(?, 'Small'),\r\n"
			+ "(?, 'Small'),\r\n"
			+ "(?, 'Small'),\r\n"
			+ "(?, 'Small'),\r\n"
			+ "(?, 'Small'),\r\n"
			+ "(?, 'Small'),\r\n"
			+ "(?, 'Small'),\r\n"
			+ "(?, 'Small'),\r\n"
			+ "(?, 'Small'),\r\n"
			+ "(?, 'Small'),\r\n"
			+ "(?, 'Small'),\r\n"
			+ "(?, 'Small'),\r\n"
			+ "(?, 'Small'),\r\n"
			+ "(?, 'Small'),\r\n"
			+ "\r\n"
			+ "-- 20 righe per Large\r\n"
			+ "(?, 'Large'),\r\n"
			+ "(?, 'Large'),\r\n"
			+ "(?, 'Large'),\r\n"
			+ "(?, 'Large'),\r\n"
			+ "(?, 'Large'),\r\n"
			+ "(?, 'Large'),\r\n"
			+ "(?, 'Large'),\r\n"
			+ "(?, 'Large'),\r\n"
			+ "(?, 'Large'),\r\n"
			+ "(?, 'Large'),\r\n"
			+ "(?, 'Large'),\r\n"
			+ "(?, 'Large'),\r\n"
			+ "(?, 'Large'),\r\n"
			+ "(?, 'Large'),\r\n"
			+ "(?, 'Large'),\r\n"
			+ "(?, 'Large'),\r\n"
			+ "(?, 'Large'),\r\n"
			+ "(?, 'Large'),\r\n"
			+ "(?, 'Large'),\r\n"
			+ "(?, 'Large'),\r\n"
			+ "\r\n"
			+ "-- 20 righe per Medium\r\n"
			+ "(?, 'Medium'),\r\n"
			+ "(?, 'Medium'),\r\n"
			+ "(?, 'Medium'),\r\n"
			+ "(?, 'Medium'),\r\n"
			+ "(?, 'Medium'),\r\n"
			+ "(?, 'Medium'),\r\n"
			+ "(?, 'Medium'),\r\n"
			+ "(?, 'Medium'),\r\n"
			+ "(?, 'Medium'),\r\n"
			+ "(?, 'Medium'),\r\n"
			+ "(?, 'Medium'),\r\n"
			+ "(?, 'Medium'),\r\n"
			+ "(?, 'Medium'),\r\n"
			+ "(?, 'Medium'),\r\n"
			+ "(?, 'Medium'),\r\n"
			+ "(?, 'Medium'),\r\n"
			+ "(?, 'Medium'),\r\n"
			+ "(?, 'Medium'),\r\n"
			+ "(?, 'Medium'),\r\n"
			+ "(?, 'Medium'),\r\n"
			+ "\r\n"
			+ "-- 10 righe per Extra Large\r\n"
			+ "(?, 'Extra Large'),\r\n"
			+ "(?, 'Extra Large'),\r\n"
			+ "(?, 'Extra Large'),\r\n"
			+ "(?, 'Extra Large'),\r\n"
			+ "(?, 'Extra Large'),\r\n"
			+ "(?, 'Extra Large'),\r\n"
			+ "(?, 'Extra Large'),\r\n"
			+ "(?, 'Extra Large'),\r\n"
			+ "(?, 'Extra Large'),\r\n"
			+ "(?, 'Extra Large');\r\n";
	ResultSet rs = null;
	try (Connection conn = this.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
		for (int i = 1; i < 66; i++) {
			pstmt.setInt(i, idModel);
		}	
		pstmt.executeUpdate();
	}
}
public int returnIdModel(Model model) throws SQLException {
	String sql = "SELECT * FROM Model WHERE name = ? AND price = ? AND category = ? AND description = ?;";
	ResultSet rs = null;
    try (Connection conn = this.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, model.getName());
        pstmt.setDouble(2, model.getPrice());
        pstmt.setString(3, model.getCategory());
        pstmt.setString(4, model.getDescription());
        rs = pstmt.executeQuery();
        return rs.getInt("ID");
    }
    
}
}
	
	

 


