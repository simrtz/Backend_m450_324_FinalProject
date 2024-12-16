-- Create the database
CREATE DATABASE IF NOT EXISTS TodoDatabase;

-- Use the created database
USE TodoDatabase;

-- Create a table
CREATE TABLE IF NOT EXISTS todo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    completed BOOLEAN NOT NULL,
    priority VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL
);