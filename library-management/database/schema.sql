-- Library Management System Database Schema

CREATE DATABASE IF NOT EXISTS library_management;

USE library_management;

-- Books table
CREATE TABLE books (
    book_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn VARCHAR(20) UNIQUE,
    category VARCHAR(100),
    total_copies INT DEFAULT 1,
    available_copies INT DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Users table
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    address TEXT,
    user_type ENUM('STUDENT', 'FACULTY', 'STAFF') DEFAULT 'STUDENT',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Book issues table
CREATE TABLE book_issues (
    issue_id INT PRIMARY KEY AUTO_INCREMENT,
    book_id INT,
    user_id INT,
    issue_date DATE NOT NULL,
    due_date DATE NOT NULL,
    return_date DATE NULL,
    late_fee DECIMAL(10, 2) DEFAULT 0.00,
    status ENUM(
        'ISSUED',
        'RETURNED',
        'OVERDUE'
    ) DEFAULT 'ISSUED',
    FOREIGN KEY (book_id) REFERENCES books (book_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

-- Insert sample data
INSERT INTO
    books (
        title,
        author,
        isbn,
        category,
        total_copies,
        available_copies
    )
VALUES (
        'Java: The Complete Reference',
        'Herbert Schildt',
        '9780071808552',
        'Programming',
        5,
        5
    ),
    (
        'Clean Code',
        'Robert C. Martin',
        '9780132350884',
        'Programming',
        3,
        3
    ),
    (
        'Database System Concepts',
        'Abraham Silberschatz',
        '9780078022159',
        'Database',
        4,
        4
    );

INSERT INTO
    users (name, email, phone, user_type)
VALUES (
        'John Doe',
        'john.doe@email.com',
        '1234567890',
        'STUDENT'
    ),
    (
        'Jane Smith',
        'jane.smith@email.com',
        '0987654321',
        'FACULTY'
    ),
    (
        'Bob Johnson',
        'bob.johnson@email.com',
        '5555555555',
        'STAFF'
    );