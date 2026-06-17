\c notificaciones;

DROP TABLE IF EXISTS logs_notificaciones;
DROP TABLE IF EXISTS notificaciones;
DROP TABLE IF EXISTS usuarios_proyeccion;

CREATE TABLE usuarios_proyeccion (
    email VARCHAR(120) PRIMARY KEY,
    nombre VARCHAR(80),
    activo BOOLEAN DEFAULT TRUE
);

CREATE TABLE notificaciones (
    codigo VARCHAR(20) PRIMARY KEY,
    usuario_email VARCHAR(120) NOT NULL,
    canal VARCHAR(20),
    mensaje VARCHAR(255),
    enviada BOOLEAN DEFAULT FALSE,

    CONSTRAINT fk_usuario_notificacion
        FOREIGN KEY (usuario_email)
        REFERENCES usuarios_proyeccion(email)
);

CREATE TABLE logs_notificaciones (
    id SERIAL PRIMARY KEY,
    notificacion_codigo VARCHAR(20) NOT NULL,
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(30),
    detalle TEXT,

    CONSTRAINT fk_log_notificacion
        FOREIGN KEY (notificacion_codigo)
        REFERENCES notificaciones(codigo)
);

-- =========================================================
-- DATOS DE PRUEBA
-- =========================================================

-- Usuarios Proyección
INSERT INTO usuarios_proyeccion
(email, nombre, activo)
VALUES
('admin@tripplanner.cl', 'Administrador', TRUE),

('cliente1@gmail.com', 'Cliente Uno', TRUE),

('operador@viajesandinos.com', 'Operador Andes', TRUE),

('user@exploramundo.com', 'Usuario Explora', TRUE),

('invitado@oldtravels.com', 'Invitado Demo', FALSE);

-- =========================================================
-- NOTIFICACIONES
-- =========================================================

INSERT INTO notificaciones
(codigo, usuario_email, canal, mensaje, enviada)
VALUES

(
    'NOTI001',
    'admin@tripplanner.cl',
    'EMAIL',
    'Bienvenido al panel administrativo',
    TRUE
),

(
    'NOTI002',
    'cliente1@gmail.com',
    'SMS',
    'Tu reserva fue confirmada',
    FALSE
),

(
    'NOTI003',
    'operador@viajesandinos.com',
    'EMAIL',
    'Nueva solicitud de servicio recibida',
    TRUE
),

(
    'NOTI004',
    'user@exploramundo.com',
    'PUSH',
    'Hay nuevas ofertas disponibles',
    FALSE
),

(
    'NOTI005',
    'cliente1@gmail.com',
    'EMAIL',
    'Tu pago fue aprobado exitosamente',
    TRUE
);

-- =========================================================
-- LOGS NOTIFICACIONES
-- =========================================================

INSERT INTO logs_notificaciones
(notificacion_codigo, fecha, estado, detalle)
VALUES

(
    'NOTI001',
    '2026-05-20 10:30:00',
    'ENVIADA',
    'Correo enviado correctamente'
),

(
    'NOTI002',
    '2026-05-21 11:00:00',
    'PENDIENTE',
    'Esperando procesamiento de cola'
),

(
    'NOTI003',
    '2026-05-22 14:15:00',
    'ENVIADA',
    'Notificación enviada al operador'
),

(
    'NOTI004',
    '2026-05-23 16:45:00',
    'ERROR',
    'Dispositivo destino no disponible'
),

(
    'NOTI005',
    '2026-05-24 09:20:00',
    'ENVIADA',
    'Confirmación de pago enviada'
);