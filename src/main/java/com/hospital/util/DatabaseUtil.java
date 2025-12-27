package com.hospital.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseUtil {
    private static final Logger LOGGER = Logger.getLogger(DatabaseUtil.class.getName());
    private static final Properties properties = new Properties();
    
    static {
        try (InputStream input = DatabaseUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                LOGGER.severe("Sorry, unable to find db.properties");
                throw new RuntimeException("Unable to find db.properties");
            }
            properties.load(input);
            
            // Load the JDBC driver
            Class.forName(properties.getProperty("jdbc.driver"));
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error initializing database configuration", e);
            throw new RuntimeException("Failed to initialize database configuration", e);
        }
    }
    
    public static Connection getConnection() throws SQLException {
        String url = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");
        
        return DriverManager.getConnection(url, username, password);
    }
    
    // Helper method to close resources
    public static void close(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Error closing resource", e);
                }
            }
        }
    }
    
    // Helper method to rollback a connection
    public static void rollback(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error rolling back transaction", e);
            }
        }
    }
}
