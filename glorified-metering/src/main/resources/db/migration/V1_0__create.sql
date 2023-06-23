CREATE SCHEMA metering;

CREATE TABLE metering.temperature (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	type ENUM ('CPU', 'GPU'),
	temperature DECIMAL (6, 2)
);
