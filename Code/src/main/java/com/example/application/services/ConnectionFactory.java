package com.example.application.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * La classe ConnectionFactory fornisce un metodo per ottenere una connessione al database SQLite.
 * Viene utilizzata per gestire la connessione e attivare le chiavi esterne nel database.
 * <p>
 * Questa classe utilizza il pattern di Factory per centralizzare la gestione delle connessioni.
 * </p>
 * 
 * @author [Il tuo nome]
 * @version 1.0
 */
public class ConnectionFactory {
    
    /** Logger per tracciare i messaggi di log della classe */
    private static final Logger logger = LogManager.getLogger(ConnectionFactory.class);
    
    /** URL del database SQLite */
    private static final String URL = "jdbc:sqlite:databases/db.db";

    /**
     * Restituisce una connessione al database SQLite.
     * <p>
     * La connessione attiva l'opzione `PRAGMA foreign_keys = ON;` per garantire il rispetto dei vincoli di chiave esterna.
     * </p>
     * 
     * @return un oggetto {@link Connection} con la connessione attiva al database
     * @throws SQLException se si verifica un errore durante la connessione
     */
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL);
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");
            }
            logger.info("Connessione al database riuscita");
        } catch (SQLException e) {
            logger.error("Errore durante la connessione al database: " + e.getMessage(), e);
            throw e;
        }
        return connection;
    }
}
