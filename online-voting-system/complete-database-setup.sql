-- Complete MySQL Database Setup for Election System
-- Run this script in MySQL to set up everything from scratch

-- Drop database if exists and create fresh
DROP DATABASE IF EXISTS election_db;

CREATE DATABASE election_db;

USE election_db;

-- Users table for voter registration and admin accounts
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    national_id VARCHAR(20) UNIQUE NOT NULL,
    role ENUM('VOTER', 'ADMIN') DEFAULT 'VOTER',
    enabled BOOLEAN DEFAULT TRUE,
    has_voted BOOLEAN DEFAULT FALSE,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_username (username),
    INDEX idx_national_id (national_id),
    INDEX idx_role (role)
);

-- Candidates table
CREATE TABLE candidates (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    party VARCHAR(100) NOT NULL,
    description TEXT,
    photo_url VARCHAR(255),
    active BOOLEAN DEFAULT TRUE,
    vote_count INT DEFAULT 0,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_active (active),
    INDEX idx_vote_count (vote_count DESC)
);

-- Votes table for recording votes
CREATE TABLE votes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    candidate_id BIGINT NOT NULL,
    vote_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ip_address VARCHAR(45),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (candidate_id) REFERENCES candidates (id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_vote (user_id),
    INDEX idx_candidate_id (candidate_id),
    INDEX idx_vote_time (vote_time)
);

-- Insert default admin user (password: admin123)
-- The password hash is for 'admin123' using BCrypt
INSERT INTO
    users (
        username,
        password,
        email,
        full_name,
        national_id,
        role,
        enabled,
        has_voted
    )
VALUES (
        'admin',
        '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a',
        'admin@election.com',
        'System Administrator',
        'ADMIN001',
        'ADMIN',
        TRUE,
        FALSE
    );

-- Insert Indian political candidates
INSERT INTO
    candidates (
        name,
        party,
        description,
        active,
        vote_count
    )
VALUES (
        'Rahul Gandhi',
        'Indian National Congress',
        'Senior Congress leader advocating for social justice, employment generation, and inclusive development. Former MP with focus on youth empowerment and rural development.',
        TRUE,
        0
    ),
    (
        'Amit Shah',
        'Bharatiya Janata Party',
        'Senior BJP leader and former Union Home Minister. Strong advocate for national security, digital India initiatives, and economic reforms with focus on Make in India.',
        TRUE,
        0
    ),
    (
        'Arvind Kejriwal',
        'Aam Aadmi Party',
        'Chief Minister of Delhi promoting anti-corruption measures, quality education, and healthcare reforms. Former IRS officer turned politician with focus on transparent governance.',
        TRUE,
        0
    ),
    (
        'Mamata Banerjee',
        'All India Trinamool Congress',
        'Chief Minister of West Bengal championing federal rights, women empowerment, and cultural preservation. Veteran politician with focus on social welfare and regional development.',
        TRUE,
        0
    ),
    (
        'Uddhav Thackeray',
        'Shiv Sena (UBT)',
        'Former Chief Minister of Maharashtra advocating for Marathi pride, urban development, and environmental conservation. Leader promoting regional identity and sustainable growth.',
        TRUE,
        0
    );

-- Create indexes for better performance
CREATE INDEX idx_users_enabled ON users (enabled);

CREATE INDEX idx_users_has_voted ON users (has_voted);

CREATE INDEX idx_candidates_name ON candidates (name);

CREATE INDEX idx_votes_user_candidate ON votes (user_id, candidate_id);

-- Security: Create triggers to prevent vote tampering
DELIMITER /
/

CREATE TRIGGER prevent_vote_update 
BEFORE UPDATE ON votes 
FOR EACH ROW 
BEGIN 
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Vote records cannot be modified for security reasons';
END
/
/

CREATE TRIGGER prevent_vote_deletion 
BEFORE DELETE ON votes 
FOR EACH ROW 
BEGIN 
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Vote records cannot be deleted for security reasons';
END
/
/

DELIMITER;

-- Create view for election statistics
CREATE VIEW election_stats AS
SELECT (
        SELECT COUNT(*)
        FROM users
        WHERE
            role = 'VOTER'
    ) as total_voters,
    (
        SELECT COUNT(*)
        FROM users
        WHERE
            role = 'VOTER'
            AND has_voted = TRUE
    ) as voted_count,
    (
        SELECT COUNT(*)
        FROM candidates
        WHERE
            active = TRUE
    ) as active_candidates,
    (
        SELECT COUNT(*)
        FROM votes
    ) as total_votes,
    (
        SELECT ROUND(
                (
                    COUNT(*) * 100.0 /

(
                        SELECT COUNT(*)
                        FROM users
                        WHERE
                            role = 'VOTER'
                    )
                ), 2
            )
        FROM users
        WHERE
            role = 'VOTER'
            AND has_voted = TRUE
    ) as turnout_percentage;

-- Verify the setup
SELECT 'Database setup completed successfully!' as status;

SELECT 'Admin user created with username: admin, password: admin123' as admin_info;

SELECT CONCAT(
        'Total candidates inserted: ', COUNT(*)
    ) as candidates_info
FROM candidates;

SELECT 'You can now run the Spring Boot application!' as next_step;