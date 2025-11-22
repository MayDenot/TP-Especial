-- ========================================
-- Script de Población de Base de Datos MySQL
-- TP-Especial - Sistema de Alquiler de Monopatines
-- ========================================

USE `TP-Especial`;

DROP TABLE IF EXISTS account;
create table account
(
    id_account       bigint auto_increment
        primary key,
    activa           bit          null,
    fecha_de_alta    datetime(6)  null,
    monto_disponible double       null,
    tipo_cuenta      varchar(255) null
);

DROP TABLE IF EXISTS rate;
create table rate
(
    id           bigint auto_increment
        primary key,
    fecha_inicio datetime(6) null,
    tarifa       double      null,
    tarifa_extra double      null,
    constraint UK366jw31fqc26w9tq1vfxt12wd
        unique (fecha_inicio)
);

DROP TABLE IF EXISTS stop;
create table stop
(
    id_stop   bigint auto_increment
        primary key,
    direccion varchar(255) null,
    latitud   float        null,
    longitud  float        null,
    nombre    varchar(255) null
);

DROP TABLE IF EXISTS travel;
create table travel
(
    id_travel         bigint auto_increment
        primary key,
    costo             double       null,
    fecha_hora_fin    datetime(6)  null,
    fecha_hora_inicio datetime(6)  null,
    km_recorridos     int          null,
    monopatin         varchar(255) null,
    parada_fin        bigint       null,
    parada_inicio     bigint       null,
    pausado           bit          null,
    usuario           bigint       null
);

DROP TABLE IF EXISTS pause;
create table pause
(
    id_pause  int auto_increment
        primary key,
    duracion  int    null,
    id_travel bigint null,
    constraint FKeyvi0y6iuahymlyf7sjtd92yg
        foreign key (id_travel) references travel (id_travel)
);

DROP TABLE IF EXISTS user;
create table user
(
    id_user        bigint auto_increment
        primary key,
    apellido       varchar(255)                                       null,
    email          varchar(255)                                       null,
    latitud        float                                              null,
    longitud       float                                              null,
    nombre         varchar(255)                                       null,
    nombre_usuario varchar(255)                                       null,
    numero_celular int                                                null,
    rol            enum ('ADMINISTRADOR', 'MANTENIMIENTO', 'USUARIO') null
);

DROP TABLE IF EXISTS user_account;
create table user_account
(
    id_user    bigint not null,
    id_account bigint not null,
    constraint FK1kkcfdw12r21riltr9msy6yxv
        foreign key (id_user) references user (id_user),
    constraint FKfu9xoirldrwuwxy24wqbj3wck
        foreign key (id_account) references account (id_account)
);

-- ========================================
-- 1. TABLA: user
-- ========================================
INSERT INTO user (id_user, nombre_usuario, nombre, apellido, email, numero_celular, rol, latitud, longitud) VALUES
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

-- ========================================
-- 2. TABLA: account
-- ========================================
INSERT INTO account (id_account, fecha_de_alta, monto_disponible, activa, tipo_cuenta) VALUES
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

-- ========================================
-- 3. TABLA: user_account (Relación Many-to-Many)
-- ========================================
INSERT INTO user_account (id_user, id_account) VALUES
-- Juan Pérez tiene 2 cuentas
(1, 1),
(1, 2),
-- María Rodríguez tiene 1 cuenta
(2, 3),
-- Carlos Gómez tiene 1 cuenta
(3, 4),
-- Laura Martínez tiene 2 cuentas
(4, 5),
(4, 8),
-- Roberto López tiene 1 cuenta
(5, 10),
-- Ana García (Admin) tiene 1 cuenta
(6, 6),
-- Pedro Díaz (Mantenimiento) tiene 1 cuenta
(7, 7),
-- Sofía Fernández tiene 1 cuenta
(8, 11),
-- Diego Romero tiene 1 cuenta (inactiva)
(9, 9),
-- Valentina Morales tiene 2 cuentas
(10, 12),
(10, 13),
-- Fernando Ruiz (Mantenimiento) tiene 1 cuenta
(11, 7),
-- Isabel Torres tiene 1 cuenta
(12, 14);

-- ========================================
-- 4. TABLA: stop (Paradas de monopatines)
-- ========================================
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

-- ========================================
-- 5. TABLA: rate (Tarifas)
-- ========================================
INSERT INTO rate (id, tarifa, tarifa_extra, fecha_inicio) VALUES
(2, 60.00, 120.00, '2024-03-10 00:00:00'),
(3, 70.00, 140.00, '2024-06-05 00:00:00'),
(4, 80.00, 160.00, '2024-09-01 00:00:00'),
(5, 85.00, 170.00, '2025-01-02 00:00:00'),
(6, 50.00, 100.00, '2024-01-03 00:00:00');

-- ========================================
-- 6. TABLA: travel (Viajes realizados)
-- ========================================
-- Nota: Los IDs de monopatín hacen referencia a documentos en MongoDB
INSERT INTO travel (id_travel, fecha_hora_inicio, fecha_hora_fin, km_recorridos, pausado, parada_inicio, parada_fin, monopatin, usuario, costo) VALUES
-- Viajes completados
(1, '2024-09-15 08:30:00', '2024-09-15 08:50:00', 5, FALSE, 1, 4, 'SCOOTER-001', 2, 100.50),
(2, '2024-09-15 09:15:00', '2024-09-15 09:40:00', 7, FALSE, 2, 4, 'SCOOTER-002', 3, 140.75),
(3, '2024-09-15 10:00:00', '2024-09-15 10:25:00', 4, FALSE, 1, 4, 'SCOOTER-003', 4, 80.25),
(4, '2024-09-15 11:30:00', '2024-09-15 12:10:00', 12, FALSE, 3, 4, 'SCOOTER-004', 5, 240.00),
(5, '2024-09-15 14:00:00', '2024-09-15 14:35:00', 8, FALSE, 5, 4, 'SCOOTER-001', 6, 160.50),
(6, '2024-09-16 08:45:00', '2024-09-16 09:20:00', 9, FALSE, 6, 4, 'SCOOTER-005', 7, 180.75),
(7, '2024-09-16 10:30:00', '2024-09-16 11:00:00', 6, FALSE, 7, 4, 'SCOOTER-006', 8, 120.00),
(8, '2024-09-16 12:00:00', '2024-09-16 12:45:00', 11, FALSE, 2, 4, 'SCOOTER-002', 9, 220.25),
(9, '2024-09-16 15:15:00', '2024-09-16 15:50:00', 7, FALSE, 9, 4, 'SCOOTER-007', 10, 140.50),
(10, '2024-09-16 16:30:00', '2024-09-16 17:15:00', 13, FALSE, 1, 4, 'SCOOTER-003', 3, 260.75),
(11, '2024-09-17 08:00:00', '2024-09-17 08:30:00', 5, FALSE, 4, 4, 'SCOOTER-008', 1, 100.00),
(12, '2024-09-17 09:45:00', '2024-09-17 10:20:00', 8, FALSE, 5, 4, 'SCOOTER-001', 2, 160.25),
(13, '2024-09-17 11:00:00', '2024-09-17 11:40:00', 10, FALSE, 3, 4, 'SCOOTER-004', 6, 200.50),
(14, '2024-09-17 13:30:00', '2024-09-17 14:15:00', 9, FALSE, 7, 4, 'SCOOTER-009', 9, 180.00),
(15, '2024-09-17 15:00:00', '2024-09-17 15:35:00', 6, FALSE, 10, 4, 'SCOOTER-005', 8, 120.75),
(16, '2024-09-18 08:30:00', '2024-09-18 09:15:00', 11, FALSE, 2, 4, 'SCOOTER-010', 4, 220.50),
(17, '2024-09-18 10:00:00', '2024-09-18 10:45:00', 12, FALSE, 6, 4, 'SCOOTER-006', 1, 240.25),
(18, '2024-09-18 12:30:00', '2024-09-18 13:00:00', 5, FALSE, 1, 4, 'SCOOTER-002', 5, 100.75),
(19, '2024-09-18 14:45:00', '2024-09-18 15:30:00', 10, FALSE, 9, 4, 'SCOOTER-007', 3, 200.00),
(20, '2024-09-18 16:00:00', '2024-09-18 16:50:00', 14, FALSE, 8, 4, 'SCOOTER-003', 10, 280.50),
-- Viajes con pausas
(21, '2024-09-19 08:15:00', '2024-09-19 09:30:00', 15, TRUE, 1, 4, 'SCOOTER-001', 7, 300.75),
(22, '2024-09-19 10:00:00', '2024-09-19 11:15:00', 13, TRUE, 4, 4, 'SCOOTER-004', 9, 260.25),
(23, '2024-09-19 13:00:00', '2024-09-19 14:30:00', 18, TRUE, 5, 4, 'SCOOTER-008', 10, 360.50),
(24, '2024-09-19 15:30:00', '2024-09-19 17:00:00', 20, TRUE, 2, 4, 'SCOOTER-005', 6, 400.00),
(25, '2024-09-19 17:30:00', '2024-09-19 18:45:00', 16, TRUE, 3, 4, 'SCOOTER-009', 8, 320.75),
-- Viaje en curso (sin fecha de fin)
(26, '2024-11-07 18:00:00', NULL, 3, FALSE, 1, NULL, 'SCOOTER-001', 2, NULL);

-- ========================================
-- 7. TABLA: pause (Pausas durante viajes)
-- ========================================
INSERT INTO pause (id_pause, duracion, id_travel) VALUES
-- Pausas para viaje 21 (Juan Pérez)
(1, 10, 21),  -- Pausa de 10 minutos (08:35 a 08:45)
(2, 15, 21),  -- Pausa de 15 minutos (09:00 a 09:15)
-- Pausas para viaje 22 (Laura Martínez)
(3, 15, 22),  -- Pausa de 15 minutos (10:30 a 10:45)
-- Pausas para viaje 23 (Roberto López)
(4, 20, 23),  -- Pausa de 20 minutos (13:20 a 13:40)
(5, 15, 23),  -- Pausa de 15 minutos (14:00 a 14:15)
-- Pausas para viaje 24 (Sofía Fernández)
(6, 20, 24),  -- Pausa de 20 minutos (16:00 a 16:20)
-- Pausas para viaje 25 (Valentina Morales)
(7, 10, 25),  -- Pausa de 10 minutos (18:00 a 18:10)
(8, 10, 25);  -- Pausa de 10 minutos (18:25 a 18:35)

-- ========================================
-- Verificación de datos insertados
-- ========================================
SELECT 'Usuarios insertados:' AS Info, COUNT(*) AS Total FROM user;
SELECT 'Cuentas insertadas:' AS Info, COUNT(*) AS Total FROM account;
SELECT 'Relaciones user_account:' AS Info, COUNT(*) AS Total FROM user_account;
SELECT 'Paradas insertadas:' AS Info, COUNT(*) AS Total FROM stop;
SELECT 'Tarifas insertadas:' AS Info, COUNT(*) AS Total FROM rate;
SELECT 'Viajes insertados:' AS Info, COUNT(*) AS Total FROM travel;
SELECT 'Pausas insertadas:' AS Info, COUNT(*) AS Total FROM pause;

SELECT '¡Base de datos MySQL poblada exitosamente!' AS Resultado;
