package com.chatapp.client;

import com.chatapp.model.ChatRoom;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.*;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ChatController {
    @FXML private ListView<ChatRoom> roomListView;
    @FXML private VBox messageContainer;
    @FXML private TextField messageField;
    @FXML private Button sendButton;
    @FXML private Label currentRoomLabel;
    @FXML private Button createRoomButton;
    @FXML private ScrollPane messageScrollPane;
    @FXML private VBox sidebar;
    @FXML private Button toggleSidebarButton;
    
    private int userId;
    private String username;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private int currentRoomId = -1;
    private Map<Integer, ChatRoom> rooms = new HashMap<>();
    private boolean sidebarVisible = true;
    
    public void initialize(int userId, String username, Socket socket, BufferedReader reader, PrintWriter writer) {
        this.userId = userId;
        this.username = username;
        this.socket = socket;
        this.reader = reader;
        this.writer = writer;
        
        setupUI();
        startMessageListener();
        
        // Request room list when initialized
        System.out.println("Requesting room list for user: " + username);
        writer.println("GET_ROOMS:" + userId);
    }
    
    private void setupUI() {
        messageField.setOnAction(e -> sendMessage());
        sendButton.setOnAction(e -> sendMessage());
        
        roomListView.getSelectionModel().selectedItemProperty().addListener((obs, oldRoom, newRoom) -> {
            if (newRoom != null) {
                joinRoom(newRoom.getId());
            }
        });
        
        createRoomButton.setOnAction(e -> showCreateRoomDialog());
        toggleSidebarButton.setOnAction(e -> toggleSidebar());
        
        // Auto-scroll to bottom
        messageContainer.heightProperty().addListener((obs, oldVal, newVal) -> {
            messageScrollPane.setVvalue(1.0);
        });
    }
    
    private void startMessageListener() {
        Thread listenerThread = new Thread(() -> {
            try {
                String message;
                while ((message = reader.readLine()) != null) {
                    handleServerMessage(message);
                }
            } catch (IOException e) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Connection Error");
                    alert.setHeaderText("Lost connection to server");
                    alert.showAndWait();
                });
            }
        });
        listenerThread.setDaemon(true);
        listenerThread.start();
    }
    
    private void handleServerMessage(String message) {
        Platform.runLater(() -> {
            String[] parts = message.split(":", 4);
            String command = parts[0];
            
            switch (command) {
                case "ROOM_LIST":
                    updateRoomList(parts[1]);
                    break;
                case "MESSAGE":
                    if (parts.length >= 5) {
                        displayMessage(parts[1], parts[2], Integer.parseInt(parts[3]), parts[4]);
                    } else {
                        displayMessage(parts[1], parts[2], Integer.parseInt(parts[3]), null);
                    }
                    break;
                case "HISTORY":
                    displayMessage(parts[1], parts[2], Integer.parseInt(parts[3]), null);
                    break;
                case "MESSAGE_STATUS":
                    updateMessageStatus(Integer.parseInt(parts[1]), parts[2], Integer.parseInt(parts[3]));
                    break;
                case "ROOM_CREATED":
                    // Refresh room list after creating a room
                    writer.println("GET_ROOMS:" + userId);
                    break;
            }
        });
    }
    
    private void updateRoomList(String roomData) {
        roomListView.getItems().clear();
        rooms.clear();
        
        if (!roomData.isEmpty()) {
            String[] roomEntries = roomData.split(";");
            for (String entry : roomEntries) {
                if (!entry.isEmpty()) {
                    String[] roomInfo = entry.split(",");
                    int id = Integer.parseInt(roomInfo[0]);
                    String name = roomInfo[1];
                    boolean isPrivate = Boolean.parseBoolean(roomInfo[2]);
                    
                    ChatRoom room = new ChatRoom(id, name, isPrivate, 0);
                    rooms.put(id, room);
                    roomListView.getItems().add(room);
                }
            }
        }
    }
    
    private void joinRoom(int roomId) {
        currentRoomId = roomId;
        ChatRoom room = rooms.get(roomId);
        currentRoomLabel.setText(room != null ? room.getName() : "Room " + roomId);
        messageContainer.getChildren().clear();
        writer.println("JOIN_ROOM:" + roomId);
    }
    
    private void displayMessage(String senderName, String content, int roomId) {
        displayMessage(senderName, content, roomId, null);
    }
    
    private void displayMessage(String senderName, String content, int roomId, String messageId) {
        if (roomId != currentRoomId) return;
        
        HBox messageBox = new HBox();
        messageBox.setPadding(new Insets(5, 10, 5, 10));
        messageBox.setSpacing(10);
        
        boolean isOwnMessage = senderName.equals(username);
        
        if (isOwnMessage) {
            messageBox.setAlignment(Pos.CENTER_RIGHT);
            VBox messageContent = createMessageBubble(content, senderName, "#3498db", Color.WHITE, "SENT");
            if (messageId != null) {
                messageContent.setUserData(messageId); // Store message ID for status updates
            }
            messageBox.getChildren().add(messageContent);
        } else {
            messageBox.setAlignment(Pos.CENTER_LEFT);
            VBox messageContent = createMessageBubble(content, senderName, "#ecf0f1", Color.BLACK, null);
            messageBox.getChildren().add(messageContent);
        }
        
        messageContainer.getChildren().add(messageBox);
    }
    
    private VBox createMessageBubble(String content, String senderName, String bgColor, Color textColor) {
        return createMessageBubble(content, senderName, bgColor, textColor, null);
    }
    
    private VBox createMessageBubble(String content, String senderName, String bgColor, Color textColor, String status) {
        VBox bubble = new VBox();
        bubble.setMaxWidth(300);
        bubble.setPadding(new Insets(8, 12, 8, 12));
        bubble.setStyle("-fx-background-color: " + bgColor + "; -fx-background-radius: 15;");
        
        Label senderLabel = new Label(senderName);
        senderLabel.setFont(Font.font("System", FontWeight.BOLD, 11));
        senderLabel.setTextFill(textColor.darker());
        
        Label contentLabel = new Label(content);
        contentLabel.setWrapText(true);
        contentLabel.setTextFill(textColor);
        contentLabel.setFont(Font.font("System", 13));
        
        // Create bottom row with time and status
        HBox bottomRow = new HBox();
        bottomRow.setSpacing(5);
        bottomRow.setAlignment(Pos.CENTER_RIGHT);
        
        Label timeLabel = new Label(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        timeLabel.setFont(Font.font("System", 10));
        timeLabel.setTextFill(textColor.darker());
        
        // Add status indicator for own messages
        if (status != null) {
            Label statusLabel = new Label(getStatusIcon(status));
            statusLabel.setFont(Font.font("System", 10));
            statusLabel.setTextFill(getStatusColor(status));
            statusLabel.setId("status-" + status); // For easy identification during updates
            
            bottomRow.getChildren().addAll(timeLabel, statusLabel);
        } else {
            bottomRow.getChildren().add(timeLabel);
        }
        
        bubble.getChildren().addAll(senderLabel, contentLabel, bottomRow);
        return bubble;
    }
    
    private String getStatusIcon(String status) {
        switch (status) {
            case "SENT": return "✓";
            case "DELIVERED": return "✓✓";
            case "READ": return "✓✓";
            default: return "✓";
        }
    }
    
    private Color getStatusColor(String status) {
        switch (status) {
            case "SENT": return Color.GRAY;
            case "DELIVERED": return Color.GRAY;
            case "READ": return Color.BLUE;
            default: return Color.GRAY;
        }
    }
    
    private void updateMessageStatus(int messageId, String status, int deliveredCount) {
        // Find the message bubble with this ID and update its status
        for (javafx.scene.Node messageBox : messageContainer.getChildren()) {
            if (messageBox instanceof HBox) {
                HBox hbox = (HBox) messageBox;
                for (javafx.scene.Node child : hbox.getChildren()) {
                    if (child instanceof VBox && child.getUserData() != null) {
                        VBox bubble = (VBox) child;
                        if (messageId == Integer.parseInt(child.getUserData().toString())) {
                            // Update the status label in this bubble
                            updateBubbleStatus(bubble, status, deliveredCount);
                            break;
                        }
                    }
                }
            }
        }
    }
    
    private void updateBubbleStatus(VBox bubble, String status, int deliveredCount) {
        // Find the bottom row (HBox) containing time and status
        for (javafx.scene.Node child : bubble.getChildren()) {
            if (child instanceof HBox) {
                HBox bottomRow = (HBox) child;
                // Find and update the status label
                for (javafx.scene.Node item : bottomRow.getChildren()) {
                    if (item instanceof Label && ((Label) item).getId() != null && 
                        ((Label) item).getId().startsWith("status-")) {
                        Label statusLabel = (Label) item;
                        statusLabel.setText(getStatusIcon(status) + (deliveredCount > 1 ? " (" + deliveredCount + ")" : ""));
                        statusLabel.setTextFill(getStatusColor(status));
                        statusLabel.setId("status-" + status);
                        break;
                    }
                }
                break;
            }
        }
    }
    
    @FXML
    private void sendMessage() {
        String content = messageField.getText().trim();
        if (!content.isEmpty() && currentRoomId != -1) {
            writer.println("SEND_MESSAGE:" + currentRoomId + ":" + content);
            messageField.clear();
        }
    }
    
    private void toggleSidebar() {
        if (sidebarVisible) {
            // Hide sidebar
            sidebar.setVisible(false);
            sidebar.setManaged(false);
            toggleSidebarButton.setText("☰");
            sidebarVisible = false;
        } else {
            // Show sidebar
            sidebar.setVisible(true);
            sidebar.setManaged(true);
            toggleSidebarButton.setText("✕");
            sidebarVisible = true;
        }
    }
    
    private void showCreateRoomDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Create New Room");
        dialog.setHeaderText("Enter room details");
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField nameField = new TextField();
        nameField.setPromptText("Room name");
        CheckBox privateCheckBox = new CheckBox("Private room");
        
        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(privateCheckBox, 1, 1);
        
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK && !nameField.getText().trim().isEmpty()) {
                writer.println("CREATE_ROOM:" + nameField.getText().trim() + ":" + privateCheckBox.isSelected());
            }
        });
    }
}