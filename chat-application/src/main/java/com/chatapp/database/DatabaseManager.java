package com.chatapp.database;

import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/chatapp?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "12345";
    
    private static DatabaseManager instance;
    private Connection connection;
    
    private DatabaseManager() {
        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            establishConnection();
            initializeTables();
            System.out.println("✅ Database initialized successfully!");
        } catch (Exception e) {
            System.err.println("❌ Database initialization failed: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Cannot initialize database", e);
        }
    }
    
    private void establishConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            System.out.println("🔄 Establishing database connection...");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("✅ Database connected successfully!");
        }
    }
    
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    
    private void initializeTables() {
        try {
            Statement stmt = connection.createStatement();
            
            // Users table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    username VARCHAR(50) UNIQUE NOT NULL,
                    password VARCHAR(255) NOT NULL,
                    email VARCHAR(100),
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    is_online BOOLEAN DEFAULT FALSE
                )
            """);
            
            // Chat rooms table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS chat_rooms (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    is_private BOOLEAN DEFAULT FALSE,
                    created_by INT,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (created_by) REFERENCES users(id)
                )
            """);
            
            // Messages table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS messages (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    sender_id INT NOT NULL,
                    room_id INT NOT NULL,
                    content TEXT NOT NULL,
                    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    status VARCHAR(20) DEFAULT 'SENT',
                    FOREIGN KEY (sender_id) REFERENCES users(id),
                    FOREIGN KEY (room_id) REFERENCES chat_rooms(id)
                )
            """);
            
            // Add status column if it doesn't exist (for existing databases)
            try {
                stmt.execute("ALTER TABLE messages ADD COLUMN status VARCHAR(20) DEFAULT 'SENT'");
            } catch (SQLException e) {
                // Column already exists, ignore
            }
            
            // Room members table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS room_members (
                    room_id INT,
                    user_id INT,
                    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    PRIMARY KEY (room_id, user_id),
                    FOREIGN KEY (room_id) REFERENCES chat_rooms(id),
                    FOREIGN KEY (user_id) REFERENCES users(id)
                )
            """);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public synchronized Connection getConnection() {
        try {
            // Check if connection is still valid
            if (connection == null || connection.isClosed() || !connection.isValid(5)) {
                System.out.println("⚠️ Database connection lost, reconnecting...");
                establishConnection();
            }
            return connection;
        } catch (SQLException e) {
            System.err.println("❌ Database connection error: " + e.getMessage());
            try {
                establishConnection();
                return connection;
            } catch (SQLException ex) {
                throw new RuntimeException("Cannot establish database connection", ex);
            }
        }
    }
    
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("🔒 Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
}