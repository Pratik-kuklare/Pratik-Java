# Real-Time Chat Application

A modern real-time chat application built with Java, JavaFX, Socket programming, and MySQL database.

## Features

- **User Authentication**: Secure login and registration with password hashing
- **Real-time Messaging**: Instant message delivery using Java sockets
- **Private & Group Chat**: Support for both private conversations and group rooms
- **Message History**: Persistent message storage and retrieval
- **Modern UI**: Beautiful JavaFX interface with custom styling
- **Online Status**: Track user online/offline status
- **Room Management**: Create and join chat rooms

## Technologies Used

- **Java 17**: Core programming language
- **JavaFX**: Modern UI framework
- **Socket Programming**: Real-time communication
- **MySQL**: Database for persistent storage
- **BCrypt**: Password hashing for security
- **Maven**: Build and dependency management

## Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

## Setup Instructions

### 1. Database Setup

1. Install MySQL and start the service
2. Run the database setup script:
   ```bash
   mysql -u root -p < database-setup.sql
   ```
3. Update database credentials in `DatabaseManager.java` if needed

### 2. Build the Application

```bash
mvn clean compile
```

### 3. Run the Server

```bash
mvn exec:java -Dexec.mainClass="com.chatapp.server.ChatServer"
```

### 4. Run the Client

```bash
mvn javafx:run
```

## Usage

### First Time Setup

1. Start the chat server
2. Launch the client application
3. Register a new account using the "Register" tab
4. Login with your credentials

### Using the Chat

1. **Join Rooms**: Select a room from the sidebar to join
2. **Send Messages**: Type in the message field and press Enter or click Send
3. **Create Rooms**: Click the "+" button to create new chat rooms
4. **View History**: Message history loads automatically when joining rooms

## Architecture

### Server Components

- **ChatServer**: Main server handling client connections
- **ClientHandler**: Manages individual client sessions
- **DatabaseManager**: Handles database operations

### Client Components

- **LoginController**: Manages authentication UI
- **ChatController**: Main chat interface
- **Models**: User, Message, and ChatRoom data models

### Database Schema

- **users**: User accounts and authentication
- **chat_rooms**: Chat room information
- **messages**: Message storage with timestamps
- **room_members**: User-room relationships

## Security Features

- Password hashing using BCrypt
- SQL injection prevention with prepared statements
- Input validation and sanitization

## UI Features

- Modern flat design with custom CSS
- Color-coded message bubbles
- Real-time message timestamps
- Responsive layout
- Smooth scrolling message history

## Default Credentials

- Username: `admin`
- Password: `password`

## Customization

### Styling
Modify `src/main/resources/css/style.css` to customize the appearance.

### Database Configuration
Update connection details in `DatabaseManager.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/chatapp";
private static final String USERNAME = "your_username";
private static final String PASSWORD = "your_password";
```

### Server Port
Change the server port in `ChatServer.java`:
```java
private static final int PORT = 12345;
```

## Troubleshooting

### Common Issues

1. **Connection Refused**: Ensure the server is running before starting clients
2. **Database Connection**: Verify MySQL is running and credentials are correct
3. **JavaFX Runtime**: Make sure JavaFX is properly configured for your Java version

### Logs

Server logs are printed to console. Check for:
- Client connection/disconnection messages
- Database operation results
- Error messages and stack traces

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is open source and available under the MIT License.