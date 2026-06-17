-- 1. Conectarse a la base de datos del microservicio
\c pagos;

-- 2. Eliminación de tablas en orden jerárquico inverso
DROP TABLE IF EXISTS transacciones;
DROP TABLE IF EXISTS metodos_pago;
DROP TABLE IF EXISTS cuentas;

-- 3. Creación de tablas

CREATE TABLE cuentas (
    id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL,
    saldo NUMERIC(12,2) NOT NULL CHECK (saldo >= 0),
    moneda CHAR(3) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uq_cuenta_usuario UNIQUE (usuario_id, moneda)
);

CREATE INDEX idx_cuentas_usuario ON cuentas(usuario_id);

CREATE TABLE metodos_pago (
    id SERIAL PRIMARY KEY,
    tipo VARCHAR(50) NOT NULL, -- tarjeta, transferencia, wallet
    proveedor VARCHAR(100) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uq_metodo UNIQUE (tipo, proveedor)
);

CREATE TABLE transacciones (
    id SERIAL PRIMARY KEY,
    cuenta_id INT NOT NULL,
    metodo_id INT NOT NULL,
    monto NUMERIC(10,2) NOT NULL CHECK (monto >= 0),
    estado VARCHAR(20) NOT NULL,
    CONSTRAINT fk_cuenta FOREIGN KEY (cuenta_id) REFERENCES cuentas(id),
    CONSTRAINT fk_metodo FOREIGN KEY (metodo_id) REFERENCES metodos_pago(id),
    CONSTRAINT chk_estado CHECK (estado IN ('pendiente','aprobada','rechazada')),
    CONSTRAINT uq_tx UNIQUE (cuenta_id, metodo_id, monto)
);

CREATE INDEX idx_transacciones_cuenta ON transacciones(cuenta_id);
CREATE INDEX idx_transacciones_metodo ON transacciones(metodo_id);

-- 4. Poblar tablas con datos de prueba

-- Cuentas
INSERT INTO cuentas (usuario_id, saldo, moneda, activo) VALUES
(1, 500.00, 'CLP', TRUE),
(2, 0.00, 'CLP', TRUE), -- caso borde: saldo 0
(3, 1200.50, 'USD', TRUE),
(4, 300.00, 'CLP', FALSE); -- cuenta inactiva

-- Métodos de pago
INSERT INTO metodos_pago (tipo, proveedor, activo) VALUES
('tarjeta', 'Visa', TRUE),
('tarjeta', 'Mastercard', TRUE),
('transferencia', 'BancoEstado', TRUE),
('wallet', 'MercadoPago', TRUE),
('tarjeta', 'Amex', FALSE); -- método inactivo

-- Transacciones
INSERT INTO transacciones (cuenta_id, metodo_id, monto, estado) VALUES
(1, 1, 150.00, 'aprobada'),
(1, 2, 50.00, 'pendiente'),
(2, 3, 0.00, 'aprobada'), -- caso borde: monto 0
(3, 4, 200.75, 'rechazada'),
(4, 1, 100.00, 'aprobada'); -- cuenta inactiva