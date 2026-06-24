-- =========================================================
-- MICROSERVICIO USUARIOS
-- =========================================================

\c usuarios;

-- =========================================================
-- ELIMINAR TABLAS
-- =========================================================

DROP TABLE IF EXISTS usuarios CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS organizaciones CASCADE;

-- =========================================================
-- ORGANIZACIONES
-- =========================================================

CREATE TABLE organizaciones (

    id SERIAL PRIMARY KEY,

    nombre VARCHAR(120) NOT NULL UNIQUE,

    dominio_email VARCHAR(120) UNIQUE,

    activo BOOLEAN NOT NULL DEFAULT TRUE
);

-- =========================================================
-- ROLES
-- =========================================================

CREATE TABLE roles (

    id SERIAL PRIMARY KEY,

    nombre VARCHAR(50) NOT NULL UNIQUE,

    descripcion VARCHAR(150),

    activo BOOLEAN NOT NULL DEFAULT TRUE
);

-- =========================================================
-- USUARIOS
-- =========================================================

CREATE TABLE usuarios (

    email VARCHAR(150) PRIMARY KEY,

    nombre VARCHAR(100) NOT NULL,

    telefono VARCHAR(30),

    fecha_registro DATE NOT NULL,

    activo BOOLEAN NOT NULL DEFAULT TRUE,

    rol_id INT NOT NULL,

    organizacion_id INT,

    CONSTRAINT fk_rol
        FOREIGN KEY (rol_id)
        REFERENCES roles(id),

    CONSTRAINT fk_organizacion
        FOREIGN KEY (organizacion_id)
        REFERENCES organizaciones(id),

    CONSTRAINT chk_email_formato
        CHECK (email LIKE '%@%.%')
);

-- =========================================================
-- ÍNDICES
-- =========================================================

CREATE INDEX idx_usuarios_rol
ON usuarios(rol_id);

CREATE INDEX idx_usuarios_org
ON usuarios(organizacion_id);

-- =========================================================
-- DATOS DE PRUEBA
-- =========================================================

INSERT INTO organizaciones
(nombre, dominio_email, activo)
VALUES
('TripPlanner Chile', 'tripplanner.cl', TRUE),
('Viajes Andinos', 'viajesandinos.com', TRUE),
('Explora Mundo', NULL, TRUE),
('Old Travels', 'oldtravels.com', FALSE);

INSERT INTO roles
(nombre, descripcion, activo)
VALUES
('ROLE_ADMIN', 'Administrador del sistema', TRUE),
('ROLE_AGENTE', 'Agente de viajes y atención al cliente', TRUE),
('ROLE_OPERADOR', 'Gestiona servicios y reservas', TRUE),
('ROLE_INVITADO', 'Acceso limitado', FALSE);

INSERT INTO usuarios
(email, nombre, telefono, fecha_registro, activo, rol_id, organizacion_id)
VALUES
(
'admin@tripplanner.cl',
'Administrador',
'+56911111111',
'2026-01-10',
TRUE,
1,
1
),

(
'agente1@tripplanner.cl',
'Agente Uno',
'+56922222222',
'2026-02-05',
TRUE,
2,
1
),

(
'operador@viajesandinos.com',
'Operador Andes',
'+56933333333',
'2026-03-15',
TRUE,
3,
2
),

(
'user@exploramundo.com',
'Usuario Explora',
'+56944444444',
'2026-04-20',
TRUE,
2,
3
),

(
'invitado@oldtravels.com',
'Invitado Demo',
'+56955555555',
'2026-05-01',
FALSE,
4,
4
);