package com.example.application.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.progetto.backend.Address;
import com.example.progetto.backend.Model;
import com.example.progetto.backend.Order;
import com.example.progetto.backend.Product;
import com.example.progetto.backend.SoldProduct;
import com.example.progetto.backend.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Classe per la gestione delle operazioni sul database.
 * <p>
 * Questa classe fornisce metodi per gestire utenti, prodotti, ordini e indirizzi
 * nel database SQLite utilizzato dall'applicazione.
 * </p>
 */
public class DatabaseManager {
    private static final Logger logger = LogManager.getLogger(DatabaseManager.class);

    /**
     * Trova un utente nel database tramite il suo ID.
     *
     * @param id l'ID dell'utente da cercare
     * @return l'oggetto {@link User} se trovato, altrimenti {@code null}
     * @throws SQLException in caso di errori nella query
     */
    public User findUserByID(int id) throws SQLException {
        String query = "SELECT * FROM User WHERE ID = ?";
        try (Connection conn = ConnectionFactory.getConnection();
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
                    logger.info("Utente trovato con ID: " + id);
                    return user;
                }
            }
        } catch (SQLException e) {
            logger.error("Errore in findUserByID per ID: " + id, e);
            throw e;
        }
        return null;
    }

    /**
     * Trova un utente nel database tramite la sua email.
     *
     * @param email l'email dell'utente da cercare
     * @return l'oggetto {@link User} se trovato, altrimenti {@code null}
     * @throws SQLException in caso di errori nella query
     */
    public User findUserByEmail(String email) throws SQLException {
        String query = "SELECT * FROM User WHERE email = ?";
        try (Connection conn = ConnectionFactory.getConnection();
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
                    logger.info("Utente trovato con email: " + email);
                    return user;
                }
            }
        } catch (SQLException e) {
            logger.error("Errore in findUserByEmail per email: " + email, e);
            throw e;
        }
        return null;
    }

    /**
     * Salva un nuovo utente nel database.
     *
     * @param user l'oggetto {@link User} da salvare
     * @throws SQLException in caso di errori nella query
     */
    public void saveUser(User user) throws SQLException {
        String query = "INSERT INTO User (name, surname, email, password, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, StringUtils.capitalize(user.getName()));
            stmt.setString(2, StringUtils.capitalize(user.getSurname()));
            stmt.setString(3, user.getEmail().toLowerCase());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getRole());
            stmt.executeUpdate();
            logger.info("Utente salvato: " + user.getEmail());
        } catch (SQLException e) {
            logger.error("Errore in saveUser per email: " + user.getEmail(), e);
            throw e;
        }
    }

    /**
     * Aggiorna i dati di un utente nel database.
     *
     * @param user l'oggetto {@link User} con i dati aggiornati
     * @throws SQLException in caso di errori nella query
     */
    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE User SET name = ?, surname = ?, email = ?, password = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, StringUtils.capitalize(user.getName()));
            pstmt.setString(2, StringUtils.capitalize(user.getSurname()));
            pstmt.setString(3, user.getEmail().toLowerCase());
            pstmt.setString(4, user.getPassword());
            pstmt.setInt(5, user.getId());
            int rowsAffected = pstmt.executeUpdate();
            logger.info("Utente aggiornato: " + user.getEmail() + ", righe modificate: " + rowsAffected);
        } catch (SQLException e) {
            logger.error("Errore in updateUser per utente ID: " + user.getId(), e);
            throw e;
        }
    }

    /**
     * Restituisce tutti gli utenti presenti nel database.
     *
     * @return una lista di utenti
     * @throws SQLException in caso di errori nella query
     */
    public ArrayList<User> returnAllUser() throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM User";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
             while (rs.next()) {
                 User user = new User();
                 user.setId(rs.getInt("ID"));
                 user.setName(rs.getString("name"));
                 user.setSurname(rs.getString("surname"));
                 user.setEmail(rs.getString("email"));
                 user.setPassword(rs.getString("password"));
                 user.setRole(rs.getString("role"));
                 users.add(user);
             }
             logger.info("Tutti gli utenti ritornati");
        } catch(SQLException e) {
             logger.error("Errore in returnAllUser", e);
             throw e;
        }
        return users;
    }



    /**
     * Restituisce una lista di modelli in base alla categoria specificata.
     *
     * @param category la categoria dei modelli da cercare
     * @return una lista di oggetti {@link Model}
     * @throws SQLException in caso di errori nella query
     */
    public ArrayList<Model> returnModelsOnCategory(String category) throws SQLException {
        ArrayList<Model> models = new ArrayList<>();
        String sql = "SELECT * FROM Model WHERE category = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, category);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    models.add(new Model(
                        rs.getInt("ID"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        category,
                        rs.getString("description")
                    ));
                }
            }
            logger.info("Modelli ritornati per la categoria: " + category + ", totale: " + models.size());
        } catch (SQLException e) {
            logger.error("Errore in returnModelsOnCategory per categoria: " + category, e);
            throw e;
        }
        return models;
    }

    /**
     * Salva un nuovo modello nel database.
     *
     * @param model l'oggetto {@link Model} da salvare
     * @throws SQLException in caso di errori nella query
     */
    public void saveModel(Model model) throws SQLException {
        String query = "INSERT INTO Model (name, price, category, description) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, model.getName());
            stmt.setDouble(2, model.getPrice());
            stmt.setString(3, model.getCategory());
            stmt.setString(4, model.getDescription());
            stmt.executeUpdate();
            logger.info("Modello salvato: " + model.getName());
        } catch (SQLException e) {
            logger.error("Errore in saveModel per modello: " + model.getName(), e);
            throw e;
        }
    }

    public Model returnModelByName(String name) throws SQLException {
        String sql = "SELECT * FROM Model WHERE name = '" + name + "'";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("ID");
                    String category = rs.getString("category");
                    String description = rs.getString("description");
                    double price = rs.getDouble("price");
                    Model model = new Model(id, name, price, category, description);
                    logger.info("Modello ritornato: " + name);
                    return model;
                } else {
                    logger.warn("Nessun modello trovato con nome: " + name);
                    return null;
                }
            }
        } catch (SQLException e) {
            logger.error("Errore in returnModelByName per nome: " + name, e);
            throw e;
        }
    }

    /**
     * Restituisce un prodotto basato su un modello e una taglia specifica.
     *
     * @param model il modello del prodotto
     * @param size la taglia richiesta
     * @param qnt la quantità richiesta
     * @return un oggetto {@link Product} se disponibile, altrimenti {@code null}
     * @throws SQLException in caso di errori nella query
     */
    public Product returnProductByModel(Model model, String size, int qnt) throws SQLException {
        String sql = "SELECT * FROM Product WHERE modelID = ? AND size = ? LIMIT 1;";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, model.getId());
            pstmt.setString(2, size);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (!rs.next() || countRecords(model, size) < qnt) {
                    logger.warn("Prodotto non disponibile per modello: " + model.getName() + ", size: " + size + ", richiesta: " + qnt);
                    return null;
                }
                return new Product(rs.getInt("ID"), size, model);
            }
        } catch (SQLException e) {
            logger.error("Errore in returnProductByModel per modello: " + model.getName() + ", size: " + size, e);
            throw e;
        }
    }

    /**
     * Conta il numero di prodotti disponibili per un determinato modello e taglia.
     *
     * @param model il modello del prodotto
     * @param size la taglia richiesta
     * @return il numero di prodotti disponibili
     * @throws SQLException in caso di errori nella query
     */
    public int countRecords(Model model, String size) throws SQLException {
        int count = 0;
        String sql = "SELECT COUNT(*) AS TotalRecord FROM Product WHERE modelID = ? AND size = ?;";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, model.getId());
            pstmt.setString(2, size);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("TotalRecord");
                }
            }
            logger.info("Contatore per modello: " + model.getName() + ", size: " + size + " = " + count);
        } catch (SQLException e) {
            logger.error("Errore in countRecords per modello: " + model.getName() + ", size: " + size, e);
            throw e;
        }
        return count;
    }

    public void loadProducts(int idModel) throws SQLException {
        String sql = "INSERT INTO Product (modelID, size)\r\n"
                + "VALUES \r\n"
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
                + "(?, 'S'),\r\n"
                + "(?, 'S'),\r\n"
                + "\r\n"  
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
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 1; i < 66; i++) {
                pstmt.setInt(i, idModel);
            }
            pstmt.executeUpdate();
            logger.info("Prodotti caricati per il modello con ID: " + idModel);
        } catch (SQLException e) {
            logger.error("Errore in loadProducts per il modello con ID: " + idModel, e);
            throw e;
        }
    }

    public int returnIdModel(Model model) throws SQLException {
        String sql = "SELECT * FROM Model WHERE name = ? AND price = ? AND category = ? AND description = ?;";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, model.getName());
            pstmt.setDouble(2, model.getPrice());
            pstmt.setString(3, model.getCategory());
            pstmt.setString(4, model.getDescription());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("ID");
                    logger.info("ID modello ritornato: " + id + " per modello: " + model.getName());
                    return id;
                } else {
                    logger.warn("Nessun modello trovato in returnIdModel per: " + model.getName());
                    return -1;
                }
            }
        } catch (SQLException e) {
            logger.error("Errore in returnIdModel per modello: " + model.getName(), e);
            throw e;
        }
    }

    public void deleteModelByName(String name) throws SQLException {
        String sql = "DELETE FROM Model WHERE name = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            int rowsAffected = stmt.executeUpdate();
            logger.info("Modello eliminato: " + name + ", righe eliminate: " + rowsAffected);
        } catch (SQLException e) {
            logger.error("Errore in deleteModelByName per modello: " + name, e);
            throw e;
        }
    }

    public void saveAddress(Address address, int IDuser) throws SQLException {
        String query = "INSERT INTO Address (address, city, cap, country, IDuser) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, StringUtils.capitalizeAddress(address.getAddress()));
            stmt.setString(2, StringUtils.capitalize(address.getCity()));
            stmt.setString(3, address.getCap());
            stmt.setString(4, StringUtils.capitalize(address.getCountry()));
            stmt.setInt(5, IDuser);
            stmt.executeUpdate();
            logger.info("Indirizzo salvato per utente ID: " + IDuser);
        } catch (SQLException e) {
            logger.error("Errore in saveAddress per utente ID: " + IDuser, e);
            throw e;
        }
    }

    public void updateAddressById(Address address) throws SQLException {
        String query = "UPDATE Address SET address = ?, country = ?, cap = ?, city = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, StringUtils.capitalize(address.getAddress()));
            stmt.setString(2, StringUtils.capitalize(address.getCountry()));
            stmt.setString(3, address.getCap());
            stmt.setString(4, StringUtils.capitalize(address.getCity()));
            stmt.setInt(5, address.getID());
            int rowsAffected = stmt.executeUpdate();
            logger.info("Indirizzo aggiornato per ID: " + address.getID() + ", righe modificate: " + rowsAffected);
        } catch (SQLException e) {
            logger.error("Errore in updateAddressById per indirizzo ID: " + address.getID(), e);
            throw e;
        }
    }

    public ArrayList<Address> getAddressesByUserId(int userId) throws SQLException {
        String query = "SELECT * FROM Address WHERE IDuser = ?";
        ArrayList<Address> addresses = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Address address = new Address();
                    address.setID(rs.getInt("ID"));
                    address.setAddress(rs.getString("address"));
                    address.setCity(rs.getString("city"));
                    address.setCap(rs.getString("cap"));
                    address.setCountry(rs.getString("country"));
                    address.setIDuser(rs.getInt("IDuser"));
                    addresses.add(address);
                }
            }
            logger.info("Indirizzi ottenuti per utente ID: " + userId + ", totale: " + addresses.size());
        } catch (SQLException e) {
            logger.error("Errore in getAddressesByUserId per utente ID: " + userId, e);
            throw e;
        }
        return addresses;
    }

    public void deleteAddressById(int addressId) throws SQLException {
        String query = "DELETE FROM Address WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, addressId);
            int rowsAffected = stmt.executeUpdate();
            logger.info("Indirizzo eliminato, ID: " + addressId + ", righe eliminate: " + rowsAffected);
        } catch (SQLException e) {
            logger.error("Errore in deleteAddressById per ID: " + addressId, e);
            throw e;
        }
    }

    public ArrayList<SoldProduct> getSoldProductsByOrderId(int orderId) throws SQLException {
        String query = "SELECT * FROM SoldProducts WHERE IDorder = ?";
        ArrayList<SoldProduct> soldProducts = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
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
            logger.info("Prodotti venduti ottenuti per ordine ID: " + orderId + ", totale: " + soldProducts.size());
        } catch (SQLException e) {
            logger.error("Errore in getSoldProductsByOrderId per ordine ID: " + orderId, e);
            throw e;
        }
        return soldProducts;
    }

    public ArrayList<Order> getOrdersByUserId(int userId) throws SQLException {
        String query = "SELECT * FROM `Order` WHERE IDuser = ?";
        ArrayList<Order> orders = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    int idUser = rs.getInt("IDuser");
                    Date date = rs.getDate("date");
                    Double tot = rs.getDouble("tot");
                    Order order = new Order(id, idUser, date, tot);
                    orders.add(order);
                }
            }
            logger.info("Ordini ottenuti per utente ID: " + userId + ", totale: " + orders.size());
        } catch (SQLException e) {
            logger.error("Errore in getOrdersByUserId per utente ID: " + userId, e);
            throw e;
        }
        return orders;
    }

    public ArrayList<Order> getOrders() throws SQLException {
        String query = "SELECT * FROM `Order`";
        ArrayList<Order> orders = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    int idUser = rs.getInt("IDuser");
                    Date date = rs.getDate("date");
                    Double tot = rs.getDouble("tot");
                    Order order = new Order(id, idUser, date, tot);
                    orders.add(order);
                }
            }
            logger.info("Ordini ottenuti, totale: " + orders.size());
        } catch (SQLException e) {
            logger.error("Errore in getOrders", e);
            throw e;
        }
        return orders;
    }

    /**
     * Salva un nuovo ordine nel database.
     *
     * @param userId ID dell'utente che ha effettuato l'ordine
     * @param total importo totale dell'ordine
     * @param soldProducts lista di prodotti venduti nell'ordine
     * @throws SQLException in caso di errori nella query
     */
    public void saveOrder(int userId, double total, ArrayList<SoldProduct> soldProducts) throws SQLException {
        String query = "INSERT INTO `Order` (IDuser, tot) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setDouble(2, total);
            stmt.executeUpdate();
            try (PreparedStatement stmtID = conn.prepareStatement("SELECT LAST_INSERT_ROWID();");
                 ResultSet rs = stmtID.executeQuery()) {
                if (rs.next()) {
                    int lastId = rs.getInt(1);
                    logger.info("Ordine salvato per utente ID: " + userId + " con ID ordine: " + lastId);
                    saveSoldProducts(lastId, soldProducts);
                }
            }
        } catch (SQLException e) {
            logger.error("Errore in saveOrder per utente ID: " + userId, e);
            throw new SQLException("Errore durante il salvataggio dell'ordine", e);
        }
    }

    /**
     * Salva i prodotti venduti relativi a un ordine.
     *
     * @param orderId ID dell'ordine
     * @param soldProducts lista di prodotti venduti
     * @throws SQLException in caso di errori nella query
     */
    public void saveSoldProducts(int orderId, ArrayList<SoldProduct> soldProducts) throws SQLException {
        String query = "INSERT INTO SoldProducts (IDorder, model, size) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            for (SoldProduct product : soldProducts) {
                stmt.setInt(1, orderId);
                stmt.setString(2, product.getModel());
                stmt.setString(3, product.getSize());
                stmt.addBatch();
            }
            stmt.executeBatch();
            logger.info("Prodotti venduti salvati per ordine ID: " + orderId);
        } catch (SQLException e) {
            logger.error("Errore in saveSoldProducts per ordine ID: " + orderId, e);
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
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, startId);
            stmt.setInt(2, quantity);
            int rowsAffected = stmt.executeUpdate();
            logger.info("Prodotti eliminati a partire da ID: " + startId + " quantità: " + quantity + ", righe eliminate: " + rowsAffected);
        } catch (SQLException e) {
            logger.error("Errore in deleteProductsByIdAndQuantity per startId: " + startId + " e quantità: " + quantity, e);
            throw e;
        }
    }

    /**
     * Aggiorna la quantità di un prodotto nel database in base alla disponibilità richiesta.
     * <p>
     * Se la quantità richiesta è maggiore di quella attuale, vengono aggiunti nuovi record.
     * Se la quantità richiesta è minore, i record in eccesso vengono rimossi.
     * </p>
     *
     * @param model    il modello del prodotto
     * @param size     la taglia del prodotto
     * @param quantity la nuova quantità richiesta
     * @throws SQLException in caso di errori nella query
     */
    public void updateQuantity(Model model, String size, int quantity) throws SQLException {
        int currentQuantity = countRecords(model, size);
        Connection conn = ConnectionFactory.getConnection();
        try {
            if (quantity > currentQuantity) {
                int recordsToAdd = quantity - currentQuantity;
                String insertQuery = "INSERT INTO Product (modelID, size) VALUES (?, ?)";
                try (PreparedStatement insertStatement = conn.prepareStatement(insertQuery)) {
                    for (int i = 0; i < recordsToAdd; i++) {
                        insertStatement.setInt(1, model.getId());
                        insertStatement.setString(2, size);
                        insertStatement.addBatch();
                    }
                    int[] result = insertStatement.executeBatch();
                    logger.info("Aggiunti " + result.length + " prodotti per modello: " + model.getName() + ", size: " + size);
                }
            } else if (quantity < currentQuantity) {
                int recordsToRemove = currentQuantity - quantity;
                String deleteQuery = "DELETE FROM Product WHERE id IN (SELECT id FROM Product WHERE modelID = ? AND size = ? LIMIT ?)";
                try (PreparedStatement deleteStatement = conn.prepareStatement(deleteQuery)) {
                    deleteStatement.setInt(1, model.getId());
                    deleteStatement.setString(2, size);
                    deleteStatement.setInt(3, recordsToRemove);
                    int rowsAffected = deleteStatement.executeUpdate();
                    logger.info("Rimossi " + rowsAffected + " prodotti per modello: " + model.getName() + ", size: " + size);
                }
            }
        } catch (SQLException e) {
            logger.error("Errore in updateQuantity per modello: " + model.getName() + ", size: " + size, e);
            throw e;
        }
    }

   
}
