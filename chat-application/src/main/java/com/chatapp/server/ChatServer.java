package com.chatapp.server;

import com.chatapp.database.DatabaseManager;
import com.chatapp.model.Message;
import com.chatapp.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServer {
    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private Map<Integer, ClientHandler> clients = new ConcurrentHashMap<>();
    private DatabaseManager dbManager;
    private volatile boolean running = false;
    
    public ChatServer() {
        System.out.println("🚀 Initializing ChatServer...");
        try {
            dbManager = DatabaseManager.getInstance();
            System.out.println("✅ Database manager initialized successfully!");
            
            // Add shutdown hook for graceful shutdown
            Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
            
        } catch (Exception e) {
            System.err.println("❌ Failed to initialize database manager: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Server initialization failed", e);
        }
    }
    
    public void start() {
        try {
            System.out.println("🚀 Starting server on port " + PORT + "...");
            serverSocket = new ServerSocket(PORT);
            running = true;
            System.out.println("✅ Chat server started successfully on port " + PORT);
            System.out.println("⏳ Waiting for client connections... (Press Ctrl+C to stop)");
            
            while (running) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    if (running) { // Check if still running after accept
                        System.out.println("👤 New client connected: " + clientSocket.getInetAddress());
                        ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                        new Thread(clientHandler, "ClientHandler-" + clientSocket.getInetAddress()).start();
                    } else {
                        clientSocket.close();
                    }
                } catch (IOException e) {
                    if (running) {
                        System.err.println("❌ Error accepting client connection: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("❌ Failed to start server: " + e.getMessage());
            e.printStackTrace();
        } finally {
            shutdown();
        }
    }
    
    public void shutdown() {
        if (!running) return;
        
        System.out.println("🛑 Shutting down server...");
        running = false;
        
        try {
            // Close server socket
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                System.out.println("🔒 Server socket closed");
            }
            
            // Disconnect all clients
            System.out.println("👋 Disconnecting " + clients.size() + " clients...");
            for (ClientHandler client : clients.values()) {
                try {
                    client.disconnect();
                } catch (Exception e) {
                    System.err.println("Error disconnecting client: " + e.getMessage());
                }
            }
            clients.clear();
            
            // Close database connection
            if (dbManager != null) {
                dbManager.closeConnection();
            }
            
            System.out.println("✅ Server shutdown complete");
            
        } catch (IOException e) {
            System.err.println("❌ Error during shutdown: " + e.getMessage());
        }
    }
    
    public void addClient(int userId, ClientHandler client) {
        clients.put(userId, client);
        updateUserOnlineStatus(userId, true);
    }
    
    public void removeClient(int userId) {
        clients.remove(userId);
        updateUserOnlineStatus(userId, false);
    }
    
    public void broadcastMessage(Message message) {
        try {
            // Save message to database
            String sql = "INSERT INTO messages (sender_id, room_id, content, status) VALUES (?, ?, ?, 'SENT')";
            PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, message.getSenderId());
            stmt.setInt(2, message.getRoomId());
            stmt.setString(3, message.getContent());
            stmt.executeUpdate();
            
            // Get the generated message ID
            ResultSet keys = stmt.getGeneratedKeys();
            int messageId = 0;
            if (keys.next()) {
                messageId = keys.getInt(1);
            }
            
            // Get room members
            List<Integer> roomMembers = getRoomMembers(message.getRoomId());
            int deliveredCount = 0;
            
            // Send message to all online room members
            for (int memberId : roomMembers) {
                ClientHandler client = clients.get(memberId);
                if (client != null && memberId != message.getSenderId()) {
                    client.sendMessage("MESSAGE:" + message.getSenderName() + ":" + message.getContent() + ":" + message.getRoomId() + ":" + messageId);
                    deliveredCount++;
                }
            }
            
            // Update message status to DELIVERED if at least one person received it
            if (deliveredCount > 0) {
                updateMessageStatus(messageId, "DELIVERED");
                // Send delivery confirmation to sender
                ClientHandler senderClient = clients.get(message.getSenderId());
                if (senderClient != null) {
                    senderClient.sendMessage("MESSAGE_STATUS:" + messageId + ":DELIVERED:" + deliveredCount);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void updateMessageStatus(int messageId, String status) {
        try {
            String sql = "UPDATE messages SET status = ? WHERE id = ?";
            PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql);
            stmt.setString(1, status);
            stmt.setInt(2, messageId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private List<Integer> getRoomMembers(int roomId) {
        List<Integer> members = new ArrayList<>();
        try {
            String sql = "SELECT user_id FROM room_members WHERE room_id = ?";
            PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql);
            stmt.setInt(1, roomId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                members.add(rs.getInt("user_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }
    
    private void updateUserOnlineStatus(int userId, boolean isOnline) {
        try {
            String sql = "UPDATE users SET is_online = ? WHERE id = ?";
            PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql);
            stmt.setBoolean(1, isOnline);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new ChatServer().start();
    }
}