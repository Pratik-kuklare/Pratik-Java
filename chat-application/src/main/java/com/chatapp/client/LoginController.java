package com.chatapp.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField emailField;
    @FXML private Button loginButton;
    @FXML private Button registerButton;
    @FXML private TabPane tabPane;
    @FXML private Label statusLabel;
    
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    
    @FXML
    private void initialize() {
        connectToServer();
    }
    
    private void connectToServer() {
        try {
            System.out.println("Attempting to connect to server...");
            socket = new Socket("localhost", 12345);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            statusLabel.setText("Connected to server");
            statusLabel.setStyle("-fx-text-fill: #27ae60;");
            System.out.println("Successfully connected to server!");
        } catch (IOException e) {
            statusLabel.setText("Cannot connect to server. Make sure server is running.");
            statusLabel.setStyle("-fx-text-fill: #e74c3c;");
            System.err.println("Failed to connect to server: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        
        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please fill all fields");
            statusLabel.setStyle("-fx-text-fill: #e74c3c;");
            return;
        }
        
        writer.println("LOGIN:" + username + ":" + password);
        
        try {
            String response = reader.readLine();
            if (response.startsWith("LOGIN_SUCCESS")) {
                String[] parts = response.split(":");
                int userId = Integer.parseInt(parts[1]);
                String userName = parts[2];
                
                openChatWindow(userId, userName, socket, reader, writer);
            } else {
                statusLabel.setText("Invalid credentials");
                statusLabel.setStyle("-fx-text-fill: #e74c3c;");
            }
        } catch (IOException e) {
            statusLabel.setText("Connection error");
            statusLabel.setStyle("-fx-text-fill: #e74c3c;");
        }
    }
    
    @FXML
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String email = emailField.getText().trim();
        
        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            statusLabel.setText("Please fill all fields");
            statusLabel.setStyle("-fx-text-fill: #e74c3c;");
            return;
        }
        
        writer.println("REGISTER:" + username + ":" + password + ":" + email);
        
        try {
            String response = reader.readLine();
            if (response.equals("REGISTER_SUCCESS")) {
                statusLabel.setText("Registration successful! Please login.");
                statusLabel.setStyle("-fx-text-fill: #27ae60;");
                tabPane.getSelectionModel().select(0); // Switch to login tab
            } else {
                statusLabel.setText("Registration failed. Username may already exist.");
                statusLabel.setStyle("-fx-text-fill: #e74c3c;");
            }
        } catch (IOException e) {
            statusLabel.setText("Connection error");
            statusLabel.setStyle("-fx-text-fill: #e74c3c;");
        }
    }
    
    private void openChatWindow(int userId, String username, Socket socket, BufferedReader reader, PrintWriter writer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/chat.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            
            ChatController chatController = loader.getController();
            chatController.initialize(userId, username, socket, reader, writer);
            
            Stage chatStage = new Stage();
            chatStage.setTitle("Chat Application - " + username);
            chatStage.setScene(scene);
            chatStage.show();
            
            // Close login window
            Stage loginStage = (Stage) loginButton.getScene().getWindow();
            loginStage.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}