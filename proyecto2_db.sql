CREATE DATABASE mascotas;
USE mascotas;

CREATE TABLE mascotas (
    mascota_id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    especie VARCHAR(50) NOT NULL,
    fecha_nacimiento DATE
);

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

CREATE TABLE citas (
    cita_id INT PRIMARY KEY AUTO_INCREMENT,
    mascota_id INT NOT NULL,
    fecha_hora DATETIME NOT NULL,
    motivo VARCHAR(255) NOT NULL,
    estado ENUM('programada', 'completada', 'cancelada') DEFAULT 'programada',
    FOREIGN KEY (mascota_id) REFERENCES mascotas(mascota_id) ON DELETE CASCADE
);