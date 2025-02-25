package com.example.application.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionFactory {
    private static final Logger logger = LogManager.getLogger(ConnectionFactory.class);
    private static final String URL = "jdbc:sqlite:databases/db.db";

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
