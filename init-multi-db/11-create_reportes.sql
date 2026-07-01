-- 1. Conectarse a la base de datos del microservicio
\c reportes;

-- 2. Eliminación de tablas en orden jerárquico inverso
DROP TABLE IF EXISTS ejecuciones;
DROP TABLE IF EXISTS reportes;
DROP TABLE IF EXISTS reservas_proyeccion;
DROP TABLE IF EXISTS fuentes;

-- 3. Creación de tablas

CREATE TABLE fuentes (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL UNIQUE,
    tipo VARCHAR(50) NOT NULL, -- ventas, reservas, usuarios
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE reportes (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    fuente_id BIGINT NOT NULL,
    formato VARCHAR(20) NOT NULL, -- pdf, csv, json
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_fuente FOREIGN KEY (fuente_id) REFERENCES fuentes(id),
    CONSTRAINT uq_reporte UNIQUE (nombre, fuente_id)
);

CREATE INDEX idx_reportes_fuente ON reportes(fuente_id);

CREATE TABLE ejecuciones (
    id BIGSERIAL PRIMARY KEY,
    reporte_id BIGINT NOT NULL,
    fecha_ejecucion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(20) NOT NULL,
    url_resultado VARCHAR(255),
    CONSTRAINT fk_reporte FOREIGN KEY (reporte_id) REFERENCES reportes(id),
    CONSTRAINT chk_estado CHECK (estado IN ('pendiente','generado','fallido'))
);

CREATE INDEX idx_ejecuciones_reporte ON ejecuciones(reporte_id);
CREATE INDEX idx_ejecuciones_estado ON ejecuciones(estado);

CREATE TABLE reservas_proyeccion (
    id VARCHAR(20) PRIMARY KEY,
    cliente_id VARCHAR(20) NOT NULL,
    estado VARCHAR(20) NOT NULL
);

-- 4. Poblar tablas con datos de prueba

-- Fuentes
INSERT INTO fuentes (nombre, tipo, activo) VALUES
('Ventas Sistema', 'ventas', TRUE),
('Reservas Sistema', 'reservas', TRUE),
('Usuarios Sistema', 'usuarios', TRUE),
('Legacy Data', 'externo', FALSE); -- fuente inactiva

-- Reportes
INSERT INTO reportes (nombre, fuente_id, formato, activo) VALUES
('Reporte Ventas Mensual', 1, 'pdf', TRUE),
('Reporte Reservas Diario', 2, 'csv', TRUE),
('Reporte Usuarios Activos', 3, 'json', TRUE),
('Reporte Legacy', 4, 'pdf', FALSE); -- reporte inactivo

-- Ejecuciones
INSERT INTO ejecuciones (reporte_id, fecha_ejecucion, estado, url_resultado) VALUES
(1, '2026-05-01 08:00:00', 'generado', 'http://files/reportes/ventas_mayo.pdf'),
(2, '2026-05-01 09:00:00', 'generado', 'http://files/reportes/reservas_dia.csv'),
(3, '2026-05-01 10:00:00', 'fallido', NULL), -- error en generación
(1, CURRENT_TIMESTAMP, 'pendiente', NULL), -- ejecución en curso
(4, '2026-04-01 07:00:00', 'generado', 'http://files/reportes/legacy.pdf'); -- fuente inactiva