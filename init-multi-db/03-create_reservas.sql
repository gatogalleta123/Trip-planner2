-- 1. Conectarse a la base de datos del microservicio
\c reservas;

-- 2. Eliminación de tablas en orden jerárquico inverso
DROP TABLE IF EXISTS reservas;
DROP TABLE IF EXISTS servicios;
DROP TABLE IF EXISTS clientes;

-- 3. Creación de tablas

CREATE TABLE clientes (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE servicios (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    tipo VARCHAR(50) NOT NULL, -- hotel, vuelo, tour
    precio_base NUMERIC(10,2) NOT NULL CHECK (precio_base >= 0),
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE reservas (
    id SERIAL PRIMARY KEY,
    cliente_id INT NOT NULL,
    servicio_id INT NOT NULL,
    fecha_reserva DATE NOT NULL,
    estado VARCHAR(20) NOT NULL,
    CONSTRAINT fk_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    CONSTRAINT fk_servicio FOREIGN KEY (servicio_id) REFERENCES servicios(id),
    CONSTRAINT chk_estado CHECK (estado IN ('pendiente','confirmada','cancelada'))
);

CREATE INDEX idx_reservas_cliente ON reservas(cliente_id);
CREATE INDEX idx_reservas_servicio ON reservas(servicio_id);
CREATE UNIQUE INDEX uq_reserva_unica ON reservas(cliente_id, servicio_id, fecha_reserva);

-- 4. Poblar tablas con datos de prueba

-- Clientes
INSERT INTO clientes (nombre, email, telefono, activo) VALUES
('Juan Pérez', 'juan.perez@email.com', '912345678', TRUE),
('María González', 'maria.gonzalez@email.com', NULL, TRUE), -- sin teléfono
('Carlos Rojas', 'carlos.rojas@email.com', '987654321', FALSE), -- cliente inactivo
('Ana Torres', 'ana.torres@email.com', '911111111', TRUE);

-- Servicios
INSERT INTO servicios (nombre, tipo, precio_base, activo) VALUES
('Hotel Santiago Centro', 'hotel', 80.00, TRUE),
('Vuelo Santiago-Buenos Aires', 'vuelo', 150.00, TRUE),
('Tour Valparaíso', 'tour', 30.00, TRUE),
('Hotel Cusco Plaza', 'hotel', 0.00, TRUE), -- caso borde: precio 0
('Tour La Paz', 'tour', 50.00, FALSE); -- servicio inactivo

-- Reservas
INSERT INTO reservas (cliente_id, servicio_id, fecha_reserva, estado) VALUES
(1, 1, '2026-05-10', 'confirmada'),
(1, 2, '2026-05-15', 'pendiente'),
(2, 3, '2026-06-01', 'confirmada'),
(3, 1, '2026-06-10', 'cancelada'), -- cliente inactivo
(4, 4, '2026-07-01', 'confirmada'),
(4, 5, '2026-07-05', 'pendiente'); -- servicio inactivo