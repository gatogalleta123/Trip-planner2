-- ============================================
-- BASE DE DATOS MS-AUTH
-- Compatible con entidades JPA actuales
-- ============================================

-- 1. Conectarse a la base de datos
\c auth;

-- ============================================
-- 2. ELIMINAR TABLAS
-- Orden inverso por foreign keys
-- ============================================

DROP TABLE IF EXISTS auditoria_login;
DROP TABLE IF EXISTS usuarios_auth;
DROP TABLE IF EXISTS metodos_autenticacion;

-- ============================================
-- 3. CREACION DE TABLAS
-- ============================================

-- ----------------------------
-- TABLA METODOS AUTENTICACION
-- ----------------------------
CREATE TABLE metodos_autenticacion (
    codigo VARCHAR(20) PRIMARY KEY,
    descripcion VARCHAR(50) NOT NULL
);

-- ----------------------------
-- TABLA USUARIOS AUTH
-- Compatible con UsuarioAuth.java
-- ----------------------------
CREATE TABLE usuarios_auth (
    email VARCHAR(120) PRIMARY KEY,
    password_hash VARCHAR(255) NOT NULL,
    metodo_codigo VARCHAR(20),
    activo BOOLEAN DEFAULT TRUE,
    ultimo_login TIMESTAMP,

    CONSTRAINT fk_usuario_metodo
    FOREIGN KEY (metodo_codigo)
    REFERENCES metodos_autenticacion(codigo)
);


CREATE INDEX idx_usuario_activo
ON usuarios_auth(activo);

-- ----------------------------
-- TABLA AUDITORIA LOGIN
-- ----------------------------
CREATE TABLE auditoria_login (
    id SERIAL PRIMARY KEY,

    usuario_email VARCHAR(120),

    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    ip_origen VARCHAR(45),

    exitoso BOOLEAN,

    CONSTRAINT fk_auditoria_usuario
        FOREIGN KEY (usuario_email)
        REFERENCES usuarios_auth(email)
        ON DELETE CASCADE
);

CREATE INDEX idx_auditoria_usuario
ON auditoria_login(usuario_email);

CREATE INDEX idx_auditoria_fecha
ON auditoria_login(fecha);

-- ============================================
-- 4. INSERTS DE METODOS AUTENTICACION
-- ============================================

INSERT INTO metodos_autenticacion
(codigo, descripcion)
VALUES
('LOCAL', 'Usuario y contraseña'),
('GOOGLE', 'Inicio con Google'),
('MICROSOFT', 'Inicio con Microsoft');

-- ============================================
-- 5. INSERTS DE USUARIOS
-- ============================================

INSERT INTO usuarios_auth (
    email,
    password_hash,
    metodo_codigo,
    activo,
    ultimo_login
)
VALUES
(
    'admin@tripplanner.cl',
    'hash_admin_secure',
    'LOCAL',
    TRUE,
    '2026-05-20 10:30:00'
),
(
    'user1@gmail.com',
    'hash_user1_secure',
    'GOOGLE',
    TRUE,
    '2026-05-22 15:10:00'
),
(
    'user2@gmail.com',
    'hash_user2_secure',
    'MICROSOFT',
    FALSE,
    '2026-04-18 08:20:00'
),
(
    'moderador@tripplanner.cl',
    'hash_mod_secure',
    'LOCAL',
    TRUE,
    CURRENT_TIMESTAMP
),
(
    'test@demo.com',
    'hash_test_demo',
    'LOCAL',
    TRUE,
    CURRENT_TIMESTAMP
);

-- ============================================
-- 6. INSERTS AUDITORIA LOGIN
-- ============================================

INSERT INTO auditoria_login (
    usuario_email,
    fecha,
    ip_origen,
    exitoso
)
VALUES
(
    'admin@tripplanner.cl',
    '2026-05-20 10:30:00',
    '192.168.1.10',
    TRUE
),
(
    'admin@tripplanner.cl',
    '2026-05-21 09:10:00',
    '192.168.1.11',
    TRUE
),
(
    'user1@gmail.com',
    '2026-05-22 15:10:00',
    '10.0.0.25',
    TRUE
),
(
    'user1@gmail.com',
    '2026-05-22 15:15:00',
    '10.0.0.25',
    FALSE
),
(
    'user2@gmail.com',
    '2026-05-18 08:20:00',
    '172.16.0.3',
    FALSE
),
(
    'moderador@tripplanner.cl',
    CURRENT_TIMESTAMP,
    '127.0.0.1',
    TRUE
);
-- ============================================
-- FIN SCRIPT
-- ============================================