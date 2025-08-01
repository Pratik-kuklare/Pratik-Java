-- Create database
CREATE DATABASE IF NOT EXISTS chatapp;

USE chatapp;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_online BOOLEAN DEFAULT FALSE
);

-- Create chat rooms table
CREATE TABLE IF NOT EXISTS chat_rooms (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    is_private BOOLEAN DEFAULT FALSE,
    created_by INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES users (id)
);

-- Create messages table
CREATE TABLE IF NOT EXISTS messages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sender_id INT NOT NULL,
    room_id INT NOT NULL,
    content TEXT NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sender_id) REFERENCES users (id),
    FOREIGN KEY (room_id) REFERENCES chat_rooms (id)
);

-- Create room members table
CREATE TABLE IF NOT EXISTS room_members (
    room_id INT,
    user_id INT,
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (room_id, user_id),
    FOREIGN KEY (room_id) REFERENCES chat_rooms (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

-- Insert sample data
INSERT INTO
    users (username, password, email)
VALUES (
        'admin',
        '$2a$10$N9qo8uLOickgx2ZMRZoMye1VQ5HpHuH2ULVk/TBaO.LVUVfZ6flzS',
        'admin@chat.com'
    );

INSERT INTO
    chat_rooms (name, is_private, created_by)
VALUES ('General', FALSE, 1),
    ('Random', FALSE, 1);

INSERT INTO room_members (room_id, user_id) VALUES (1, 1), (2, 1);