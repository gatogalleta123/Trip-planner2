-- 1. Conectarse a la base de datos del microservicio
\c destinos;

-- 2. Eliminación de tablas en orden jerárquico inverso
DROP TABLE IF EXISTS atracciones;
DROP TABLE IF EXISTS destinos;
DROP TABLE IF EXISTS paises;

-- 3. Creación de tablas

CREATE TABLE paises (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    codigo_iso CHAR(2) NOT NULL UNIQUE,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE destinos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    pais_id INT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_pais FOREIGN KEY (pais_id) REFERENCES paises(id),
    CONSTRAINT uq_destino UNIQUE (nombre, pais_id)
);

CREATE INDEX idx_destinos_pais ON destinos(pais_id);

CREATE TABLE atracciones (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    destino_id INT NOT NULL,
    precio NUMERIC(10,2) CHECK (precio >= 0),
    disponible BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_destino FOREIGN KEY (destino_id) REFERENCES destinos(id),
    CONSTRAINT uq_atraccion UNIQUE (nombre, destino_id)
);

CREATE INDEX idx_atracciones_destino ON atracciones(destino_id);

-- 4. Poblar tablas con datos de prueba

-- Paises
INSERT INTO paises (nombre, codigo_iso, activo) VALUES
('Chile', 'CL', TRUE),
('Argentina', 'AR', TRUE),
('Perú', 'PE', TRUE),
('Bolivia', 'BO', FALSE); -- caso borde: país inactivo

-- Destinos
INSERT INTO destinos (nombre, pais_id, tipo, activo) VALUES
('Santiago', 1, 'ciudad', TRUE),
('Valparaíso', 1, 'costa', TRUE),
('Mendoza', 2, 'ciudad', TRUE),
('Cusco', 3, 'historico', TRUE),
('La Paz', 4, 'ciudad', FALSE); -- caso borde: destino inactivo

-- Atracciones
INSERT INTO atracciones (nombre, destino_id, precio, disponible) VALUES
('Cerro San Cristóbal', 1, 0, TRUE), -- gratis
('Sky Costanera', 1, 15.50, TRUE),
('Tour Viña del Mar', 2, 25.00, TRUE),
('Bodega Tour', 3, 40.00, TRUE),
('Machu Picchu', 4, 100.00, TRUE),
('Valle de la Luna', 5, 10.00, FALSE), -- atracción no disponible
('Plaza de Armas', 1, 0, TRUE); -- repetición válida en distinto contexto
