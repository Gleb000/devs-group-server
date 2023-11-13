CREATE TABLE IF NOT EXISTS measurement (
    id BIGSERIAL PRIMARY KEY,
    measurement_value FLOAT NOT NULL,
    raining BOOLEAN NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS sensor (
    id BIGSERIAL PRIMARY KEY,
    sensor_name VARCHAR(30) NOT NULL,
    sensor_key VARCHAR(128) NOT NULL,
    measurement INT8,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS sensor_measurement (
    sensor_id INT8 NOT NULL,
    measurement_id INT8 NOT NULL,
    FOREIGN KEY (sensor_id) REFERENCES sensor (id),
    FOREIGN KEY (measurement_id) REFERENCES measurement (id)
);