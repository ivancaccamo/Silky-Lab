package com.example.application.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.example.progetto.backend.Address;
import com.example.progetto.backend.Model;
import com.example.progetto.backend.Order;
import com.example.progetto.backend.Product;
import com.example.progetto.backend.SoldProduct;
import com.example.progetto.backend.User;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;

import jakarta.validation.constraints.AssertFalse.List;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:databases/db.db";
	

    public Connection getConnection() throws SQLException {	
    	 Connection connection = null;
         try {
            
             connection = DriverManager.getConnection(URL);
            
             try (Statement stmt = connection.createStatement()) {
                 stmt.execute("PRAGMA foreign_keys = ON;"); 
             }
         } catch (SQLException e) {
             System.err.println("Errore durante la connessione al database: " + e.getMessage());
         }
         return connection;
    }
    public User findUserByID(int id) throws SQLException {
        String query = "SELECT * FROM User WHERE ID = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
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
        	stmt.setString(1, capitalize(user.getName()));
        	stmt.setString(2, capitalize(user.getSurname()));
        	stmt.setString(3, user.getEmail().toLowerCase()); 
        	stmt.setString(4, user.getPassword()); 
        	stmt.setString(5, user.getRole());
            stmt.executeUpdate();
        }
    }
    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE User SET name = ?, surname = ?, email = ?, password = ? WHERE id = ?";
        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setString(1, capitalize(user.getName()));
        	pstmt.setString(2, capitalize(user.getSurname()));
        	pstmt.setString(3, user.getEmail().toLowerCase()); 
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
        
        if (!rs.next()||countRecords(model,size)<qnt) { 
            int id = rs.getInt("ID");
            return null;
        }

       
        int id = rs.getInt("ID");
       
        Product product = new Product(id, size, model);
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
					if(rs.next()) {
						count = rs.getInt("TotalRecord");	
					}else {
						count = 0;
					}				
					}
	return count;
	}
public void loadProducts(int idModel) throws SQLException {
	String sql = "INSERT INTO Product (modelID, size)\r\n"
			+ "VALUES \r\n"
			+ "-- 15 righe per Small\r\n"
			+ "(?, 'S'),\r\n"
			+ "(?, 'S'),\r\n"
			+ "(?, 'S'),\r\n"
			+ "(?, 'S'),\r\n"
			+ "(?, 'S'),\r\n"
			+ "(?, 'S'),\r\n"
			+ "(?, 'S'),\r\n"
			+ "(?, 'S'),\r\n"
			+ "(?, 'S'),\r\n"
			+ "(?, 'S'),\r\n"
			+ "(?, 'S'),\r\n"
			+ "(?, 'S'),\r\n"
			+ "(?, 'S'),\r\n"
			+ "(?, 'S'),\r\n"
			+ "(?, 'S'),\r\n"
			+ "\r\n"
			+ "-- 20 righe per Large\r\n"
			+ "(?, 'L'),\r\n"
			+ "(?, 'L'),\r\n"
			+ "(?, 'L'),\r\n"
			+ "(?, 'L'),\r\n"
			+ "(?, 'L'),\r\n"
			+ "(?, 'L'),\r\n"
			+ "(?, 'L'),\r\n"
			+ "(?, 'L'),\r\n"
			+ "(?, 'L'),\r\n"
			+ "(?, 'L'),\r\n"
			+ "(?, 'L'),\r\n"
			+ "(?, 'L'),\r\n"
			+ "(?, 'L'),\r\n"
			+ "(?, 'L'),\r\n"
			+ "(?, 'L'),\r\n"
			+ "(?, 'L'),\r\n"
			+ "(?, 'L'),\r\n"
			+ "(?, 'L'),\r\n"
			+ "(?, 'L'),\r\n"
			+ "(?, 'L'),\r\n"
			+ "\r\n"
			+ "-- 20 righe per Medium\r\n"
			+ "(?, 'M'),\r\n"
			+ "(?, 'M'),\r\n"
			+ "(?, 'M'),\r\n"
			+ "(?, 'M'),\r\n"
			+ "(?, 'M'),\r\n"
			+ "(?, 'M'),\r\n"
			+ "(?, 'M'),\r\n"
			+ "(?, 'M'),\r\n"
			+ "(?, 'M'),\r\n"
			+ "(?, 'M'),\r\n"
			+ "(?, 'M'),\r\n"
			+ "(?, 'M'),\r\n"
			+ "(?, 'M'),\r\n"
			+ "(?, 'M'),\r\n"
			+ "(?, 'M'),\r\n"
			+ "(?, 'M'),\r\n"
			+ "(?, 'M'),\r\n"
			+ "(?, 'M'),\r\n"
			+ "(?, 'M'),\r\n"
			+ "(?, 'M'),\r\n"
			+ "\r\n"
			+ "-- 10 righe per Extra Large\r\n"
			+ "(?, 'XL'),\r\n"
			+ "(?, 'XL'),\r\n"
			+ "(?, 'XL'),\r\n"
			+ "(?, 'XL'),\r\n"
			+ "(?, 'XL'),\r\n"
			+ "(?, 'XL'),\r\n"
			+ "(?, 'XL'),\r\n"
			+ "(?, 'XL'),\r\n"
			+ "(?, 'XL'),\r\n"
			+ "(?, 'XL');\r\n";
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
public void deleteModelByName(String name) throws SQLException {
	String sql = "DELETE FROM Model WHERE name = ?";
	
	Connection conn = this.getConnection();
	try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	    stmt.setString(1, name); 
	    stmt.executeUpdate();
	}
}


public void saveAddress(Address address, int IDuser) throws SQLException {
    String query = "INSERT INTO Address (address, city, cap, country, IDuser) VALUES (?, ?, ?, ?, ?)";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
    	stmt.setString(1, capitalizeAddress(address.getAddress()));
    	stmt.setString(2, capitalize(address.getCity()));
    	stmt.setString(3, address.getCap());
    	stmt.setString(4, capitalize(address.getCountry()));
        stmt.setInt(5, IDuser);
        stmt.executeUpdate();
    }
}

public void updateAddressById(Address address) throws SQLException {
    String query = "UPDATE Address SET address = ?, country = ?, cap = ?, city = ? WHERE id = ?";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
    	stmt.setString(1, capitalize(address.getAddress()));
    	stmt.setString(2, capitalize(address.getCountry()));
    	stmt.setString(3, address.getCap()); 
    	stmt.setString(4, capitalize(address.getCity()));
        stmt.setInt(5, address.getID());
        stmt.executeUpdate();
    }
}

public ArrayList<Address> getAddressesByUserId(int userId) throws SQLException {
    String query = "SELECT * FROM Address WHERE IDuser = ?";
    ArrayList<Address> addresses = new ArrayList<>();

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, userId);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Address address = new Address();
                address.setID(rs.getInt("ID"));
                address.setAddress(rs.getString("address"));
                address.setCity(rs.getString("city"));
                address.setCap(rs.getString("cap")); // CAP gestito come stringa
                address.setCountry(rs.getString("country"));
                address.setIDuser(rs.getInt("IDuser"));
                addresses.add(address);
            }
        }
    }
    return addresses;
}

public void deleteAddressById(int addressId) throws SQLException {
    String query = "DELETE FROM Address WHERE id = ?";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, addressId);
        stmt.executeUpdate();
    }
}

public ArrayList<SoldProduct> getSoldProductsByOrderId(int orderId) throws SQLException {
    String query = "SELECT * FROM SoldProducts WHERE IDorder = ?";
    ArrayList<SoldProduct> soldProducts = new ArrayList<>();

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, orderId);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                SoldProduct product = new SoldProduct();
                product.setID(rs.getInt("ID"));
                product.setIDorder(rs.getInt("IDorder"));
                product.setModel(rs.getString("model"));
                product.setSize(rs.getString("size"));
                soldProducts.add(product);
            }
        }
    }

    return soldProducts;
}
public ArrayList<Order> getOrdersByUserId(int userId) throws SQLException {
    String query = "SELECT * FROM `Order` WHERE IDuser = ?";
    ArrayList<Order> orders = new ArrayList<>();

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, userId);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                
                int id = rs.getInt("ID");
                int idUser = rs.getInt("IDuser");
                Date date = rs.getDate("date");
                Double tot = rs.getDouble("tot");
                Order order = new Order(id,idUser,date,tot);
                orders.add(order);
            }
        }
    }

    return orders;
}
public ArrayList<Order> getOrders() throws SQLException {
    String query = "SELECT * FROM `Order`";
    ArrayList<Order> orders = new ArrayList<>();
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {   
                int id = rs.getInt("ID");
                int idUser = rs.getInt("IDuser");
                Date date = rs.getDate("date");
                Double tot = rs.getDouble("tot");
                Order order = new Order(id,idUser,date,tot);
                orders.add(order);
            }
        }
    }

    return orders;
}
public void saveOrder(int userId, double total, ArrayList<SoldProduct> soldProducts) throws SQLException {
    String query = "INSERT INTO `Order` (IDuser, tot) VALUES (?, ?)";
    
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, userId);  
        stmt.setDouble(2, total);  
        stmt.executeUpdate();  
        
        Statement stmtID = conn.createStatement();
        ResultSet rs = stmtID.executeQuery("SELECT LAST_INSERT_ROWID();");
        	if (rs.next()) {
        	        int lastId = rs.getInt(1);
        	        saveSoldProducts(lastId, soldProducts);
        	    }
        	} catch (SQLException e) { 
        throw new SQLException("Errore durante il salvataggio dell'ordine", e);
        	}	
    	}
public void saveSoldProducts(int orderId, ArrayList<SoldProduct> soldProducts) throws SQLException {
    String query = "INSERT INTO SoldProducts (IDorder, model, size) VALUES (?, ?, ?)";
    
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        for (SoldProduct product : soldProducts) {
            stmt.setInt(1, orderId);                
            stmt.setString(2, product.getModel());  
            stmt.setString(3, product.getSize());   
            stmt.addBatch();                        
        }
        stmt.executeBatch();  
    } catch (SQLException e) {
        throw new SQLException("Errore durante il salvataggio dei prodotti venduti", e);
    }
}
public void deleteProductsByIdAndQuantity(int startId, int quantity) throws SQLException {
    String query = "DELETE FROM Product\r\n"
    		+ "WHERE id IN (\r\n"
    		+ "    SELECT id\r\n"
    		+ "    FROM Product\r\n"
    		+ "    WHERE id >= ?\r\n"
    		+ "    ORDER BY id ASC\r\n"
    		+ "    LIMIT ?\r\n"
    		+ ");";
                   
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, startId);  
        stmt.setInt(2, quantity); 
        stmt.executeUpdate();
    }
}

public void updateQuantity(Model model, String size, int quantity) throws SQLException {
	int currentQuantity=countRecords(model, size);
	Connection conn = getConnection();
	if (quantity > currentQuantity) {
        
        int recordsToAdd = quantity - currentQuantity;
        
        String insertQuery = "INSERT INTO Product (modelID, size) VALUES (?, ?)";

        try (PreparedStatement insertStatement = conn.prepareStatement(insertQuery)) {
            for (int i = 0; i < recordsToAdd; i++) {
                insertStatement.setInt(1, model.getId());
                insertStatement.setString(2, size);
                insertStatement.addBatch();
            }
            insertStatement.executeBatch();
        }
    } else if (quantity < currentQuantity) {
        
        int recordsToRemove = currentQuantity - quantity;
        String deleteQuery = "DELETE FROM Product WHERE id IN (SELECT id FROM Product WHERE modelID = ? AND size = ? LIMIT ?)";
        
        try (PreparedStatement deleteStatement = conn.prepareStatement(deleteQuery)) {
            deleteStatement.setInt(1, model.getId());
            deleteStatement.setString(2, size);
            deleteStatement.setInt(3, recordsToRemove);
            deleteStatement.executeUpdate();
        }
    }
	
}


private String capitalize(String input) {
    if (input == null || input.isEmpty()) {
        return input;
    }
    return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
}
public String capitalizeAddress(String input) {
    if (input == null || input.isEmpty()) {
        return input;
    }
    StringBuilder result = new StringBuilder();
    boolean capitalizeNext = true;
    for (char c : input.toCharArray()) {
        if (Character.isWhitespace(c)) {
            capitalizeNext = true;
            result.append(c);
        } else if (capitalizeNext) {
            result.append(Character.toUpperCase(c));
            capitalizeNext = false;
        } else {
            result.append(Character.toLowerCase(c));
        }
    }
    return result.toString();
}

}
	
	

 


