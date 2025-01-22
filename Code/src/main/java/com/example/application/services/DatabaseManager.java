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
             // Connessione al database
             connection = DriverManager.getConnection(URL);
             System.out.println("Connessione al database stabilita.");

             // Abilita le chiavi esterne
             try (Statement stmt = connection.createStatement()) {
                 stmt.execute("PRAGMA foreign_keys = ON;");
                 System.out.println("Chiavi esterne abilitate.");
             }
         } catch (SQLException e) {
             System.err.println("Errore durante la connessione al database: " + e.getMessage());
         }
         return connection;
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

        if (!rs.next()||countRecords(model,size)<qnt) {
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
	    stmt.setString(1, name); // Sostituisci 'modelName' con il nome del modello
	    int rowsAffected = stmt.executeUpdate();
	    System.out.println(rowsAffected + " record(s) eliminato/i.");
	}
}
public void saveAddress(Address address, int IDuser) throws SQLException {
    String query = "INSERT INTO Address (address, city, cap, country, IDuser) VALUES (?, ?, ?, ?, ?)";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, address.getAddress());
        stmt.setString(2, address.getCity());
        stmt.setString(3, address.getCap()); // CAP gestito come stringa
        stmt.setString(4, address.getCountry());
        stmt.setInt(5, IDuser);
        stmt.executeUpdate();
    }
}

public void updateAddressById(Address address) throws SQLException {
    String query = "UPDATE Address SET address = ?, country = ?, cap = ?, city = ? WHERE id = ?";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, address.getAddress());
        stmt.setString(2, address.getCountry());
        stmt.setString(3, address.getCap()); // CAP gestito come stringa
        stmt.setString(4, address.getCity());
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
        stmt.setInt(1, userId);  // Imposta l'ID dell'utente
        stmt.setDouble(2, total);  // Imposta il totale dell'ordine
        stmt.executeUpdate();  // Esegui l'insert
        
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
            stmt.setInt(1, orderId);                // Imposta l'ID dell'ordine
            stmt.setString(2, product.getModel());  // Nome del modello
            stmt.setString(3, product.getSize());   // Taglia
            stmt.addBatch();                        // Aggiungi al batch
        }
        stmt.executeBatch();  // Esegui il batch
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
        stmt.setInt(1, startId);    // ID del primo prodotto
        stmt.setInt(2, quantity);  // Numero di record da eliminare
        stmt.executeUpdate();
    }
}

public void updateQuantity(Model model, String size, int quantity) throws SQLException {
	int currentQuantity=countRecords(model, size);
	Connection conn = getConnection();
	if (quantity > currentQuantity) {
        // Aggiungi record
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
        // Rimuovi record
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




}
	
	

 


