-- 1. Conectarse a la base de datos del microservicio
\c vuelos;

-- 2. Eliminación de tablas en orden jerárquico inverso
DROP TABLE IF EXISTS vuelos;
DROP TABLE IF EXISTS rutas;
DROP TABLE IF EXISTS aerolineas;

-- 3. Creación de tablas

CREATE TABLE aerolineas (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL UNIQUE,
    codigo_iata CHAR(2) NOT NULL UNIQUE,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE rutas (
    id SERIAL PRIMARY KEY,
    origen VARCHAR(120) NOT NULL,
    destino VARCHAR(120) NOT NULL,
    distancia_km INT CHECK (distancia_km > 0),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uq_ruta UNIQUE (origen, destino)
);

CREATE TABLE vuelos (
    id SERIAL PRIMARY KEY,
    aerolinea_id INT NOT NULL,
    ruta_id INT NOT NULL,
    fecha_salida TIMESTAMP NOT NULL,
    precio NUMERIC(10,2) NOT NULL CHECK (precio >= 0),
    CONSTRAINT fk_aerolinea FOREIGN KEY (aerolinea_id) REFERENCES aerolineas(id),
    CONSTRAINT fk_ruta FOREIGN KEY (ruta_id) REFERENCES rutas(id),
    CONSTRAINT uq_vuelo UNIQUE (aerolinea_id, ruta_id, fecha_salida)
);

CREATE INDEX idx_vuelos_aerolinea ON vuelos(aerolinea_id);
CREATE INDEX idx_vuelos_ruta ON vuelos(ruta_id);

-- 4. Poblar tablas con datos de prueba

-- Aerolíneas
INSERT INTO aerolineas (nombre, codigo_iata, activo) VALUES
('LATAM Airlines', 'LA', TRUE),
('Sky Airline', 'H2', TRUE),
('JetSMART', 'JA', TRUE),
('AeroAntigua', 'AA', FALSE); -- aerolínea inactiva

-- Rutas
INSERT INTO rutas (origen, destino, distancia_km, activo) VALUES
('Santiago', 'Buenos Aires', 1400, TRUE),
('Santiago', 'Lima', 2450, TRUE),
('Buenos Aires', 'Cusco', 3100, TRUE),
('La Paz', 'Santiago', 1900, TRUE),
('Antofagasta', 'Calama', 220, FALSE); -- ruta inactiva

-- Vuelos
INSERT INTO vuelos (aerolinea_id, ruta_id, fecha_salida, precio) VALUES
(1, 1, '2026-05-10 08:00:00', 120.00),
(1, 2, '2026-05-12 10:30:00', 0.00), -- caso borde: vuelo gratis/promoción
(2, 1, '2026-05-10 15:00:00', 110.00),
(3, 3, '2026-06-01 06:00:00', 200.00),
(4, 4, '2026-06-05 09:00:00', 150.00), -- aerolínea inactiva
(2, 5, '2026-06-10 12:00:00', 50.00); -- ruta inactiva