-- Create database
CREATE DATABASE IF NOT EXISTS student_management;

USE student_management;

-- The tables will be automatically created by Hibernate/JPA
-- This script is just for reference and initial setup

-- Sample data to get you started
-- Run this after the application creates the tables

INSERT INTO
    students (
        first_name,
        last_name,
        email,
        phone_number,
        date_of_birth,
        address,
        student_class
    )
VALUES (
        'John',
        'Doe',
        'john.doe@email.com',
        '1234567890',
        '2005-01-15',
        '123 Main St, City',
        'Grade 10A'
    ),
    (
        'Jane',
        'Smith',
        'jane.smith@email.com',
        '0987654321',
        '2005-03-20',
        '456 Oak Ave, City',
        'Grade 10A'
    ),
    (
        'Mike',
        'Johnson',
        'mike.johnson@email.com',
        '5555555555',
        '2004-07-10',
        '789 Pine Rd, City',
        'Grade 11B'
    ),
    (
        'Sarah',
        'Wilson',
        'sarah.wilson@email.com',
        '1111111111',
        '2005-05-25',
        '321 Elm St, City',
        'Grade 10B'
    ),
    (
        'David',
        'Brown',
        'david.brown@email.com',
        '2222222222',
        '2004-12-08',
        '654 Maple Ave, City',
        'Grade 11A'
    );

INSERT INTO
    marks (
        subject,
        marks_obtained,
        total_marks,
        exam_type,
        student_id
    )
VALUES (
        'Mathematics',
        85,
        100,
        'Midterm',
        1
    ),
    (
        'English',
        92,
        100,
        'Midterm',
        1
    ),
    (
        'Science',
        78,
        100,
        'Midterm',
        1
    ),
    (
        'Mathematics',
        88,
        100,
        'Final',
        1
    ),
    (
        'English',
        95,
        100,
        'Final',
        1
    ),
    (
        'Science',
        82,
        100,
        'Final',
        1
    ),
    (
        'Mathematics',
        90,
        100,
        'Midterm',
        2
    ),
    (
        'English',
        87,
        100,
        'Midterm',
        2
    ),
    (
        'Science',
        93,
        100,
        'Midterm',
        2
    ),
    (
        'Physics',
        76,
        100,
        'Midterm',
        3
    ),
    (
        'Chemistry',
        84,
        100,
        'Midterm',
        3
    ),
    (
        'Biology',
        89,
        100,
        'Midterm',
        3
    );