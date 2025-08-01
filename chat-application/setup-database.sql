-- Simple database setup script
-- Run this in MySQL command line or MySQL Workbench

-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS chatapp;
USE chatapp;

-- Create a default admin user (password is 'admin123')
INSERT IGNORE INTO users (username, password, email) VALUES 
('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMye1VQ5HpHuH2ULVk/TBaO.LVUVfZ6flzS', 'admin@chat.com');

-- Create default chat rooms
INSERT IGNORE INTO chat_rooms (name, is_private, created_by) VALUES 
('General', FALSE, 1),
('Random', FALSE, 1);

-- Add admin to default rooms
INSERT IGNORE INTO room_members (room_id, user_id) VALUES 
(1, 1),
(2, 1);

SELECT 'Database setup completed successfully!' as Status;