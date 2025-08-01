# Real-Time Chat Application - Complete Project Structure

## Project Overview
A complete real-time chat application built with Java, JavaFX, Socket programming, and MySQL.

## Features Implemented
✅ User Authentication (Login/Register)
✅ Real-time messaging with sockets
✅ Private and Group chat rooms
✅ Message history and persistence
✅ Message delivery status (✓, ✓✓, ✓✓ read)
✅ Collapsible sidebar
✅ Modern UI with custom styling
✅ Online user status tracking

## Project Structure
```
chat-application/
├── pom.xml                                    # Maven configuration
├── README.md                                  # Project documentation
├── database-setup.sql                         # Database schema
├── setup-database.sql                         # Quick setup script
├── PROJECT_STRUCTURE.md                       # This file
└── src/
    └── main/
        ├── java/com/chatapp/
        │   ├── ChatApplication.java            # Main JavaFX application
        │   ├── client/
        │   │   ├── LoginController.java        # Login/Register UI controller
        │   │   └── ChatController.java         # Main chat UI controller
        │   ├── server/
        │   │   ├── ChatServer.java            # Main server
        │   │   └── ClientHandler.java         # Client session handler
        │   ├── database/
        │   │   └── DatabaseManager.java       # Database operations
        │   └── model/
        │       ├── User.java                  # User data model
        │       ├── Message.java               # Message data model
        │       └── ChatRoom.java              # Chat room data model
        └── resources/
            ├── fxml/
            │   ├── login.fxml                 # Login UI layout
            │   └── chat.fxml                  # Chat UI layout
            └── css/
                └── style.css                  # Application styling
```

## How to Run

### Prerequisites
- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

### Setup Steps
1. Import project into VS Code
2. Install MySQL and run database-setup.sql
3. Update database credentials in DatabaseManager.java if needed
4. Run server: `mvn exec:java`
5. Run client: `mvn javafx:run`

### Default Login
- Username: admin
- Password: password (from database-setup.sql)

## Key Technologies
- Java 17
- JavaFX for UI
- Socket programming for real-time communication
- MySQL for data persistence
- BCrypt for password security
- Maven for build management

## Architecture
- **Client-Server Architecture**: Separate client and server applications
- **MVC Pattern**: Controllers handle UI logic, Models represent data
- **Database Layer**: Centralized database management
- **Real-time Communication**: Socket-based messaging
- **Security**: Password hashing and SQL injection prevention