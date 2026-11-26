CREATE DATABASE IF NOT EXISTS empleados_db;
USE empleados_db;

CREATE TABLE rol (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

CREATE TABLE empleado (
    id INT AUTO_INCREMENT PRIMARY KEY,
    legajo INT NOT NULL UNIQUE,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    cuil VARCHAR(11) NOT NULL UNIQUE,
    email VARCHAR(255),
    rol_id INT,
    FOREIGN KEY (rol_id) REFERENCES rol(id)
);

CREATE TABLE licencia (
    id INT AUTO_INCREMENT PRIMARY KEY,
    empleado_id INT NOT NULL,
    tipo ENUM('VACACIONES', 'ENFERMEDAD', 'ESTUDIO') NOT NULL,
    fecha_desde DATE NOT NULL,
    fecha_hasta DATE NOT NULL,
    motivo VARCHAR(255),
    FOREIGN KEY (empleado_id) REFERENCES empleado(id)
);

INSERT INTO rol (nombre) VALUES ('Desarrollador'), ('Analista'), ('RRHH'), ('Gerente');
