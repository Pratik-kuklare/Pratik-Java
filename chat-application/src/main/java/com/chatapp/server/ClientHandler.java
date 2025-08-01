package com.chatapp.server;

import com.chatapp.database.DatabaseManager;
import com.chatapp.model.Message;
import org.mindrot.jbcrypt.BCrypt;

import java.io.*;
import java.net.Socket;
import java.sql.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private ChatServer server;
    private DatabaseManager dbManager;
    private int userId = -1;
    private String username;
    
    public ClientHandler(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
        this.dbManager = DatabaseManager.getInstance();
        
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        try {
            String message;
            while ((message = reader.readLine()) != null) {
                handleMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }
    
    private void handleMessage(String message) {
        String[] parts = message.split(":", 4);
        String command = parts[0];
        
        switch (command) {
            case "LOGIN":
                handleLogin(parts[1], parts[2]);
                break;
            case "REGISTER":
                handleRegister(parts[1], parts[2], parts[3]);
                break;
            case "SEND_MESSAGE":
                handleSendMessage(Integer.parseInt(parts[1]), parts[2]);
                break;
            case "JOIN_ROOM":
                handleJoinRoom(Integer.parseInt(parts[1]));
                break;
            case "CREATE_ROOM":
                handleCreateRoom(parts[1], Boolean.parseBoolean(parts[2]));
                break;
            case "GET_ROOMS":
                sendRoomList();
                break;
        }
    }
    
    private void handleLogin(String username, String password) {
        try {
            String sql = "SELECT id, username, password FROM users WHERE username = ?";
            PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next() && BCrypt.checkpw(password, rs.getString("password"))) {
                this.userId = rs.getInt("id");
                this.username = rs.getString("username");
                server.addClient(userId, this);
                
                // Ensure default rooms exist and user is added to them
                ensureDefaultRooms();
                
                sendMessage("LOGIN_SUCCESS:" + userId + ":" + username);
                sendRoomList();
            } else {
                sendMessage("LOGIN_FAILED");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            sendMessage("LOGIN_FAILED");
        }
    }
    
    private void handleRegister(String username, String password, String email) {
        try {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
            PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, email);
            stmt.executeUpdate();
            sendMessage("REGISTER_SUCCESS");
        } catch (SQLException e) {
            sendMessage("REGISTER_FAILED");
        }
    }
    
    private void handleSendMessage(int roomId, String content) {
        if (userId != -1) {
            Message message = new Message(userId, username, roomId, content);
            server.broadcastMessage(message);
        }
    }
    
    private void handleJoinRoom(int roomId) {
        try {
            String sql = "INSERT IGNORE INTO room_members (room_id, user_id) VALUES (?, ?)";
            PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql);
            stmt.setInt(1, roomId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
            
            sendRoomHistory(roomId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void handleCreateRoom(String roomName, boolean isPrivate) {
        try {
            String sql = "INSERT INTO chat_rooms (name, is_private, created_by) VALUES (?, ?, ?)";
            PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, roomName);
            stmt.setBoolean(2, isPrivate);
            stmt.setInt(3, userId);
            stmt.executeUpdate();
            
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                int roomId = keys.getInt(1);
                handleJoinRoom(roomId);
                sendMessage("ROOM_CREATED:" + roomId + ":" + roomName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void sendRoomList() {
        try {
            String sql = """
                SELECT cr.id, cr.name, cr.is_private 
                FROM chat_rooms cr 
                JOIN room_members rm ON cr.id = rm.room_id 
                WHERE rm.user_id = ?
            """;
            PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            StringBuilder roomList = new StringBuilder("ROOM_LIST:");
            while (rs.next()) {
                roomList.append(rs.getInt("id")).append(",")
                       .append(rs.getString("name")).append(",")
                       .append(rs.getBoolean("is_private")).append(";");
            }
            sendMessage(roomList.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void sendRoomHistory(int roomId) {
        try {
            String sql = """
                SELECT m.content, u.username, m.timestamp 
                FROM messages m 
                JOIN users u ON m.sender_id = u.id 
                WHERE m.room_id = ? 
                ORDER BY m.timestamp ASC LIMIT 50
            """;
            PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql);
            stmt.setInt(1, roomId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String historyMessage = "HISTORY:" + rs.getString("username") + ":" + 
                                      rs.getString("content") + ":" + roomId;
                sendMessage(historyMessage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void ensureDefaultRooms() {
        try {
            // Create default rooms if they don't exist
            String[] defaultRooms = {"General", "Random"};
            
            for (String roomName : defaultRooms) {
                // Check if room exists
                String checkSql = "SELECT id FROM chat_rooms WHERE name = ?";
                PreparedStatement checkStmt = dbManager.getConnection().prepareStatement(checkSql);
                checkStmt.setString(1, roomName);
                ResultSet rs = checkStmt.executeQuery();
                
                int roomId;
                if (rs.next()) {
                    roomId = rs.getInt("id");
                } else {
                    // Create the room
                    String createSql = "INSERT INTO chat_rooms (name, is_private, created_by) VALUES (?, FALSE, 1)";
                    PreparedStatement createStmt = dbManager.getConnection().prepareStatement(createSql, Statement.RETURN_GENERATED_KEYS);
                    createStmt.setString(1, roomName);
                    createStmt.executeUpdate();
                    
                    ResultSet keys = createStmt.getGeneratedKeys();
                    if (keys.next()) {
                        roomId = keys.getInt(1);
                    } else {
                        continue;
                    }
                }
                
                // Add user to room if not already a member
                String memberSql = "INSERT IGNORE INTO room_members (room_id, user_id) VALUES (?, ?)";
                PreparedStatement memberStmt = dbManager.getConnection().prepareStatement(memberSql);
                memberStmt.setInt(1, roomId);
                memberStmt.setInt(2, userId);
                memberStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void sendMessage(String message) {
        writer.println(message);
    }
    
    public void disconnect() {
        cleanup();
    }
    
    private void cleanup() {
        System.out.println("🧹 Cleaning up client: " + (username != null ? username : "Unknown"));
        
        if (userId != -1) {
            server.removeClient(userId);
        }
        
        try {
            if (reader != null) reader.close();
        } catch (IOException e) {
            System.err.println("Error closing reader: " + e.getMessage());
        }
        
        try {
            if (writer != null) writer.close();
        } catch (Exception e) {
            System.err.println("Error closing writer: " + e.getMessage());
        }
        
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
                System.out.println("🔒 Client socket closed for: " + (username != null ? username : "Unknown"));
            }
        } catch (IOException e) {
            System.err.println("Error closing socket: " + e.getMessage());
        }
    }
}