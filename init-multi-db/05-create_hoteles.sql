-- 1. Conectarse a la base de datos del microservicio
\c hoteles;

-- 2. Eliminación de tablas en orden jerárquico inverso
DROP TABLE IF EXISTS habitaciones;
DROP TABLE IF EXISTS hoteles;
DROP TABLE IF EXISTS ciudades;

-- 3. Creación de tablas

CREATE TABLE ciudades (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    pais VARCHAR(120) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uq_ciudad UNIQUE (nombre, pais)
);

CREATE TABLE hoteles (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    ciudad_id INT NOT NULL,
    estrellas INT CHECK (estrellas BETWEEN 1 AND 5),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_ciudad FOREIGN KEY (ciudad_id) REFERENCES ciudades(id),
    CONSTRAINT uq_hotel UNIQUE (nombre, ciudad_id)
);

CREATE INDEX idx_hoteles_ciudad ON hoteles(ciudad_id);

CREATE TABLE habitaciones (
    id SERIAL PRIMARY KEY,
    hotel_id INT NOT NULL,
    tipo VARCHAR(50) NOT NULL, -- simple, doble, suite
    precio NUMERIC(10,2) NOT NULL CHECK (precio >= 0),
    disponible BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_hotel FOREIGN KEY (hotel_id) REFERENCES hoteles(id),
    CONSTRAINT uq_habitacion UNIQUE (hotel_id, tipo)
);

CREATE INDEX idx_habitaciones_hotel ON habitaciones(hotel_id);

-- 4. Poblar tablas con datos de prueba

-- Ciudades
INSERT INTO ciudades (nombre, pais, activo) VALUES
('Santiago', 'Chile', TRUE),
('Valparaíso', 'Chile', TRUE),
('Buenos Aires', 'Argentina', TRUE),
('Cusco', 'Perú', TRUE),
('La Paz', 'Bolivia', FALSE); -- ciudad inactiva

-- Hoteles
INSERT INTO hoteles (nombre, ciudad_id, estrellas, activo) VALUES
('Hotel Andes Plaza', 1, 4, TRUE),
('Hotel Costanera View', 2, 5, TRUE),
('Hotel Central BA', 3, 3, TRUE),
('Hotel Inca Palace', 4, 5, TRUE),
('Hotel Altiplano', 5, 2, FALSE); -- hotel en ciudad inactiva

-- Habitaciones
INSERT INTO habitaciones (hotel_id, tipo, precio, disponible) VALUES
(1, 'simple', 50.00, TRUE),
(1, 'doble', 80.00, TRUE),
(2, 'suite', 150.00, TRUE),
(3, 'simple', 0.00, TRUE), -- caso borde: precio 0
(4, 'suite', 200.00, TRUE),
(5, 'doble', 60.00, FALSE); -- habitación no disponible