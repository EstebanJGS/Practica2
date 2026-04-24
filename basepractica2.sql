DROP DATABASE IF EXISTS mascotas;
CREATE DATABASE mascotas;
USE mascotas;

-- 1. Nueva tabla de Usuarios para Login y Registro
CREATE TABLE usuarios (
    usuario_id INT PRIMARY KEY AUTO_INCREMENT,
    nombre_completo VARCHAR(150) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE, -- El email es único para validar el login
    password VARCHAR(255) NOT NULL      -- Almacenará la contraseña en texto plano según pediste
);

-- 2. Tabla de Mascotas (Actualizada con usuario_id)
CREATE TABLE mascotas (
    mascota_id INT PRIMARY KEY AUTO_INCREMENT,
    usuario_id INT NOT NULL,            -- Dueño de la mascota
    nombre VARCHAR(100) NOT NULL,
    especie VARCHAR(50) NOT NULL,
    fecha_nacimiento DATE,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(usuario_id) ON DELETE CASCADE
);

-- 3. Tabla de Historial Médico
CREATE TABLE historial_medico (
    historial_id INT PRIMARY KEY AUTO_INCREMENT,
    mascota_id INT NOT NULL,
    fecha_consulta DATE NOT NULL,
    diagnostico TEXT NOT NULL,
    tratamiento TEXT,
    veterinario VARCHAR(100) NOT NULL,
    notas TEXT,
    FOREIGN KEY (mascota_id) REFERENCES mascotas(mascota_id) ON DELETE CASCADE
);

-- 4. Tabla de Citas
CREATE TABLE citas (
    cita_id INT PRIMARY KEY AUTO_INCREMENT,
    mascota_id INT NOT NULL,
    fecha_hora DATETIME NOT NULL,
    motivo VARCHAR(255) NOT NULL,
    estado ENUM('programada', 'completada', 'cancelada') DEFAULT 'programada',
    FOREIGN KEY (mascota_id) REFERENCES mascotas(mascota_id) ON DELETE CASCADE
);

-- Datos de prueba para que valides tu login
INSERT INTO usuarios (nombre_completo, email, password) VALUES 
('Esteban Josué', 'esteban@correo.com', 'admin123'),
('Compañera Sistema', 'test@correo.com', '12345');