
CREATE DATABASE IF NOT EXISTS travel_db;

USE travel_db;

CREATE TABLE IF NOT EXISTS bookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50),
    customerName VARCHAR(100),
    details VARCHAR(255),
    status VARCHAR(20)
);

-- Optional sample data
INSERT INTO bookings (type, customerName, details, status) VALUES
('flight', 'Alice', 'Flight #A123', 'confirmed'),
('hotel', 'Bob', 'Hilton Downtown', 'pending'),
('cab', 'Charlie', 'Uber - TX987', 'confirmed');
