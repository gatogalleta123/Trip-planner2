-- 1. Conectarse a la base de datos del microservicio
\c resennas;

-- 2. Eliminación de tablas en orden jerárquico inverso
DROP TABLE IF EXISTS resennas;
DROP TABLE IF EXISTS destinos;
DROP TABLE IF EXISTS usuarios_resennas;

-- 3. Creación de tablas

CREATE TABLE usuarios_resennas (
    id SERIAL PRIMARY KEY,
    email VARCHAR(150) NOT NULL UNIQUE,
    nombre VARCHAR(120) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE destinos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    pais VARCHAR(120) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uq_destino UNIQUE (nombre, pais)
);

CREATE TABLE resennas (
    id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL,
    destino_id INT NOT NULL,
    calificacion INT NOT NULL CHECK (calificacion BETWEEN 1 AND 5),
    comentario VARCHAR(255),
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios_resennas(id),
    CONSTRAINT fk_destino FOREIGN KEY (destino_id) REFERENCES destinos(id),
    CONSTRAINT uq_resenna UNIQUE (usuario_id, destino_id)
);

CREATE INDEX idx_resennas_usuario ON resennas(usuario_id);
CREATE INDEX idx_resennas_destino ON resennas(destino_id);

-- 4. Poblar tablas con datos de prueba

-- Usuarios
INSERT INTO usuarios_resennas (email, nombre, activo) VALUES
('juan.perez@email.com', 'Juan Pérez', TRUE),
('maria.gonzalez@email.com', 'María González', TRUE),
('carlos.rojas@email.com', 'Carlos Rojas', FALSE), -- usuario inactivo
('ana.torres@email.com', 'Ana Torres', TRUE);

-- Destinos
INSERT INTO destinos (nombre, pais, activo) VALUES
('Santiago', 'Chile', TRUE),
('Valparaíso', 'Chile', TRUE),
('Cusco', 'Perú', TRUE),
('La Paz', 'Bolivia', FALSE); -- destino inactivo

-- Reseñas
INSERT INTO resennas (usuario_id, destino_id, calificacion, comentario) VALUES
(1, 1, 5, 'Excelente experiencia'),
(1, 2, 4, 'Muy bonito lugar'),
(2, 3, 3, 'Interesante pero concurrido'),
(3, 1, 2, 'No me gustó mucho'), -- usuario inactivo
(4, 4, 1, 'Mala experiencia'); -- destino inactivo