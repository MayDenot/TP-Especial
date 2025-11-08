-- ========================================
-- Script de Población de Base de Datos MySQL
-- TP-Especial - Sistema de Alquiler de Monopatines
-- VERSIÓN: Fresh Install (Recrea las tablas)
-- ========================================

USE `TP-Especial`;

-- ========================================
-- PASO 1: Limpiar tablas existentes
-- ========================================

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS pause;
DROP TABLE IF EXISTS travel;
DROP TABLE IF EXISTS user_account;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS stop;
DROP TABLE IF EXISTS rate;

SET FOREIGN_KEY_CHECKS = 1;

-- ========================================
-- PASO 2: Crear tablas con estructura correcta
-- ========================================

-- Tabla: user
CREATE TABLE user (
    id_user BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombreUsuario VARCHAR(255),
    nombre VARCHAR(255),
    apellido VARCHAR(255),
    email VARCHAR(255),
    numeroCelular INT,
    rol VARCHAR(50),
    latitud FLOAT,
    longitud FLOAT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla: account
CREATE TABLE account (
    id_account BIGINT AUTO_INCREMENT PRIMARY KEY,
    fechaDeAlta DATETIME,
    montoDisponible DOUBLE,
    activa BOOLEAN,
    tipoCuenta VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla: user_account (Many to Many)
CREATE TABLE user_account (
    id_user BIGINT NOT NULL,
    id_account BIGINT NOT NULL,
    PRIMARY KEY (id_user, id_account),
    FOREIGN KEY (id_user) REFERENCES user(id_user) ON DELETE CASCADE,
    FOREIGN KEY (id_account) REFERENCES account(id_account) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla: stop
CREATE TABLE stop (
    id_stop BIGINT AUTO_INCREMENT PRIMARY KEY,
    direccion VARCHAR(255),
    nombre VARCHAR(255),
    latitud FLOAT,
    longitud FLOAT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla: rate
CREATE TABLE rate (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tarifa DOUBLE,
    tarifaExtra DOUBLE,
    fecha DATETIME,
    vigente BOOLEAN
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla: travel
CREATE TABLE travel (
    id_travel BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_hora_inicio DATETIME,
    fecha_hora_fin DATETIME,
    kmRecorridos INT,
    pausado BOOLEAN,
    tarifa BIGINT,
    parada_inicio BIGINT,
    parada_fin BIGINT,
    monopatin VARCHAR(255),
    usuario BIGINT,
    FOREIGN KEY (tarifa) REFERENCES rate(id),
    FOREIGN KEY (parada_inicio) REFERENCES stop(id_stop),
    FOREIGN KEY (parada_fin) REFERENCES stop(id_stop),
    FOREIGN KEY (usuario) REFERENCES user(id_user)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla: pause
CREATE TABLE pause (
    id_pause INT AUTO_INCREMENT PRIMARY KEY,
    hora_inicio DATETIME,
    hora_fin DATETIME,
    id_travel BIGINT,
    FOREIGN KEY (id_travel) REFERENCES travel(id_travel) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ========================================
-- PASO 3: Insertar datos
-- ========================================

-- 1. TABLA: user
INSERT INTO user (id_user, nombreUsuario, nombre, apellido, email, numeroCelular, rol, latitud, longitud) VALUES
(1, 'jperez', 'Juan', 'Pérez', 'juan.perez@email.com', 1512345678, 'USUARIO', -37.3215, -59.1333),
(2, 'mrodriguez', 'María', 'Rodríguez', 'maria.rodriguez@email.com', 1523456789, 'USUARIO', -37.3256, -59.1389),
(3, 'cgomez', 'Carlos', 'Gómez', 'carlos.gomez@email.com', 1534567890, 'USUARIO', -37.3189, -59.1421),
(4, 'lmartinez', 'Laura', 'Martínez', 'laura.martinez@email.com', 1545678901, 'USUARIO', -37.3298, -59.1367),
(5, 'rlopez', 'Roberto', 'López', 'roberto.lopez@email.com', 1556789012, 'USUARIO', -37.3178, -59.1445),
(6, 'agarcia', 'Ana', 'García', 'ana.garcia@email.com', 1567890123, 'ADMINISTRADOR', -37.3234, -59.1401),
(7, 'pdiaz', 'Pedro', 'Díaz', 'pedro.diaz@email.com', 1578901234, 'MANTENIMIENTO', -37.3267, -59.1356),
(8, 'sfernandez', 'Sofía', 'Fernández', 'sofia.fernandez@email.com', 1589012345, 'USUARIO', -37.3201, -59.1412),
(9, 'dromero', 'Diego', 'Romero', 'diego.romero@email.com', 1590123456, 'USUARIO', -37.3245, -59.1378),
(10, 'vmorales', 'Valentina', 'Morales', 'valentina.morales@email.com', 1501234567, 'USUARIO', -37.3289, -59.1390),
(11, 'fruiz', 'Fernando', 'Ruiz', 'fernando.ruiz@email.com', 1512346789, 'MANTENIMIENTO', -37.3212, -59.1434),
(12, 'itorres', 'Isabel', 'Torres', 'isabel.torres@email.com', 1523457890, 'USUARIO', -37.3278, -59.1345);

-- 2. TABLA: account
INSERT INTO account (id_account, fechaDeAlta, montoDisponible, activa, tipoCuenta) VALUES
(1, '2024-01-15 10:00:00', 5000.00, TRUE, 'PREPAGA'),
(2, '2024-01-20 11:30:00', 3500.50, TRUE, 'PREPAGA'),
(3, '2024-02-05 09:15:00', 7200.75, TRUE, 'PREPAGA'),
(4, '2024-02-10 14:45:00', 2100.00, TRUE, 'CREDITO'),
(5, '2024-02-15 16:20:00', 4500.25, TRUE, 'PREPAGA'),
(6, '2024-03-01 08:00:00', 10000.00, TRUE, 'ADMIN'),
(7, '2024-03-05 12:30:00', 1500.00, TRUE, 'MANTENIMIENTO'),
(8, '2024-03-10 10:45:00', 6300.50, TRUE, 'PREPAGA'),
(9, '2024-03-15 13:20:00', 800.00, FALSE, 'PREPAGA'),
(10, '2024-03-20 15:10:00', 3900.75, TRUE, 'CREDITO'),
(11, '2024-04-01 09:30:00', 5500.00, TRUE, 'PREPAGA'),
(12, '2024-04-05 11:00:00', 4200.25, TRUE, 'PREPAGA'),
(13, '2024-04-10 14:15:00', 2800.00, TRUE, 'CREDITO'),
(14, '2024-04-15 16:45:00', 6700.50, TRUE, 'PREPAGA');

-- 3. TABLA: user_account
INSERT INTO user_account (id_user, id_account) VALUES
(1, 1), (1, 2),
(2, 3),
(3, 4),
(4, 5), (4, 8),
(5, 10),
(6, 6),
(7, 7),
(8, 11),
(9, 9),
(10, 12), (10, 13),
(11, 7),
(12, 14);

-- 4. TABLA: stop
INSERT INTO stop (id_stop, direccion, nombre, latitud, longitud) VALUES
(1, 'Av. Colón 1500', 'Parada Central', -37.3215, -59.1333),
(2, 'Calle 14 entre 5 y 6', 'Parada Norte', -37.3156, -59.1389),
(3, 'Av. Alem 2300', 'Parada Universidad', -37.3289, -59.1421),
(4, 'Calle 8 N° 950', 'Parada Plaza', -37.3298, -59.1267),
(5, 'Av. Independencia 1800', 'Parada Shopping', -37.3178, -59.1445),
(6, 'Diagonal 74 N° 320', 'Parada Hospital', -37.3334, -59.1301),
(7, 'Calle 12 y 32', 'Parada Estadio', -37.3267, -59.1556),
(8, 'Av. 7 N° 1200', 'Parada Terminal', -37.3401, -59.1312),
(9, 'Calle 50 entre 11 y 12', 'Parada Mercado', -37.3245, -59.1478),
(10, 'Av. 60 y 122', 'Parada Bosque', -37.3289, -59.1190);

-- 5. TABLA: rate
INSERT INTO rate (id, tarifa, tarifaExtra, fecha, vigente) VALUES
(1, 50.00, 100.00, '2024-01-01 00:00:00', FALSE),
(2, 60.00, 120.00, '2024-03-01 00:00:00', FALSE),
(3, 70.00, 140.00, '2024-06-01 00:00:00', FALSE),
(4, 80.00, 160.00, '2024-09-01 00:00:00', TRUE),
(5, 85.00, 170.00, '2025-01-01 00:00:00', FALSE);

-- 6. TABLA: travel
INSERT INTO travel (id_travel, fecha_hora_inicio, fecha_hora_fin, kmRecorridos, pausado, tarifa, parada_inicio, parada_fin, monopatin, usuario) VALUES
(1, '2024-09-15 08:30:00', '2024-09-15 08:50:00', 5, FALSE, 4, 1, 2, 'SCOOTER-001', 1),
(2, '2024-09-15 09:15:00', '2024-09-15 09:40:00', 7, FALSE, 4, 2, 3, 'SCOOTER-002', 2),
(3, '2024-09-15 10:00:00', '2024-09-15 10:25:00', 4, FALSE, 4, 1, 4, 'SCOOTER-003', 3),
(4, '2024-09-15 11:30:00', '2024-09-15 12:10:00', 12, FALSE, 4, 3, 5, 'SCOOTER-004', 4),
(5, '2024-09-15 14:00:00', '2024-09-15 14:35:00', 8, FALSE, 4, 5, 6, 'SCOOTER-001', 5),
(6, '2024-09-16 08:45:00', '2024-09-16 09:20:00', 9, FALSE, 4, 6, 7, 'SCOOTER-005', 1),
(7, '2024-09-16 10:30:00', '2024-09-16 11:00:00', 6, FALSE, 4, 7, 8, 'SCOOTER-006', 8),
(8, '2024-09-16 12:00:00', '2024-09-16 12:45:00', 11, FALSE, 4, 2, 9, 'SCOOTER-002', 2),
(9, '2024-09-16 15:15:00', '2024-09-16 15:50:00', 7, FALSE, 4, 9, 10, 'SCOOTER-007', 10),
(10, '2024-09-16 16:30:00', '2024-09-16 17:15:00', 13, FALSE, 4, 1, 3, 'SCOOTER-003', 12),
(11, '2024-09-17 08:00:00', '2024-09-17 08:30:00', 5, FALSE, 4, 4, 1, 'SCOOTER-008', 1),
(12, '2024-09-17 09:45:00', '2024-09-17 10:20:00', 8, FALSE, 4, 5, 2, 'SCOOTER-001', 3),
(13, '2024-09-17 11:00:00', '2024-09-17 11:40:00', 10, FALSE, 4, 3, 6, 'SCOOTER-004', 4),
(14, '2024-09-17 13:30:00', '2024-09-17 14:15:00', 9, FALSE, 4, 7, 9, 'SCOOTER-009', 5),
(15, '2024-09-17 15:00:00', '2024-09-17 15:35:00', 6, FALSE, 4, 10, 8, 'SCOOTER-005', 8),
(16, '2024-09-18 08:30:00', '2024-09-18 09:15:00', 11, FALSE, 4, 2, 4, 'SCOOTER-010', 2),
(17, '2024-09-18 10:00:00', '2024-09-18 10:45:00', 12, FALSE, 4, 6, 1, 'SCOOTER-006', 10),
(18, '2024-09-18 12:30:00', '2024-09-18 13:00:00', 5, FALSE, 4, 1, 5, 'SCOOTER-002', 12),
(19, '2024-09-18 14:45:00', '2024-09-18 15:30:00', 10, FALSE, 4, 9, 3, 'SCOOTER-007', 1),
(20, '2024-09-18 16:00:00', '2024-09-18 16:50:00', 14, FALSE, 4, 8, 10, 'SCOOTER-003', 3),
(21, '2024-09-19 08:15:00', '2024-09-19 09:30:00', 15, FALSE, 4, 1, 7, 'SCOOTER-001', 1),
(22, '2024-09-19 10:00:00', '2024-09-19 11:15:00', 13, FALSE, 4, 4, 9, 'SCOOTER-004', 4),
(23, '2024-09-19 13:00:00', '2024-09-19 14:30:00', 18, FALSE, 4, 5, 10, 'SCOOTER-008', 5),
(24, '2024-09-19 15:30:00', '2024-09-19 17:00:00', 20, FALSE, 4, 2, 6, 'SCOOTER-005', 8),
(25, '2024-09-19 17:30:00', '2024-09-19 18:45:00', 16, FALSE, 4, 3, 8, 'SCOOTER-009', 10),
(26, '2024-11-07 18:00:00', NULL, 3, TRUE, 4, 1, NULL, 'SCOOTER-001', 2);

-- 7. TABLA: pause
INSERT INTO pause (id_pause, hora_inicio, hora_fin, id_travel) VALUES
(1, '2024-09-19 08:35:00', '2024-09-19 08:45:00', 21),
(2, '2024-09-19 09:00:00', '2024-09-19 09:15:00', 21),
(3, '2024-09-19 10:30:00', '2024-09-19 10:45:00', 22),
(4, '2024-09-19 13:20:00', '2024-09-19 13:40:00', 23),
(5, '2024-09-19 14:00:00', '2024-09-19 14:15:00', 23),
(6, '2024-09-19 16:00:00', '2024-09-19 16:20:00', 24),
(7, '2024-09-19 18:00:00', '2024-09-19 18:10:00', 25),
(8, '2024-09-19 18:25:00', '2024-09-19 18:35:00', 25);

-- ========================================
-- PASO 4: Verificación
-- ========================================
SELECT 'Usuarios insertados:' AS Info, COUNT(*) AS Total FROM user;
SELECT 'Cuentas insertadas:' AS Info, COUNT(*) AS Total FROM account;
SELECT 'Relaciones user_account:' AS Info, COUNT(*) AS Total FROM user_account;
SELECT 'Paradas insertadas:' AS Info, COUNT(*) AS Total FROM stop;
SELECT 'Tarifas insertadas:' AS Info, COUNT(*) AS Total FROM rate;
SELECT 'Viajes insertados:' AS Info, COUNT(*) AS Total FROM travel;
SELECT 'Pausas insertadas:' AS Info, COUNT(*) AS Total FROM pause;

SELECT '¡Base de datos MySQL poblada exitosamente!' AS Resultado;
