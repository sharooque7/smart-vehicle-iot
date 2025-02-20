CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    full_name VARCHAR(100),
    phone_number VARCHAR(15),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE vehicles (
    vehicle_id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(user_id) ON DELETE CASCADE,
    vin VARCHAR(50) UNIQUE NOT NULL,  -- Vehicle Identification Number
    model VARCHAR(50) NOT NULL,
    make VARCHAR(50) NOT NULL,
    year INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sensors (
    sensor_id SERIAL PRIMARY KEY,
    vehicle_id INT REFERENCES vehicles(vehicle_id) ON DELETE CASCADE,
    sensor_type VARCHAR(50) NOT NULL, -- e.g., Engine Temp, Speed, Brake Pressure
    sensor_name VARCHAR(50) NOT NULL, -- Name of the TDengine database
    installed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'active' -- active, inactive, faulty
);

CREATE TABLE vehicle_travel_logs (
    log_id SERIAL PRIMARY KEY,
    vehicle_id INT REFERENCES vehicles(vehicle_id) ON DELETE CASCADE,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP,
    start_location VARCHAR(255),
    end_location VARCHAR(255),
    distance_km FLOAT CHECK (distance_km >= 0),
    average_speed FLOAT CHECK (average_speed >= 0),
    status VARCHAR(20) DEFAULT 'in_progress' -- in_progress, completed, canceled
);
