-- Crear base de datos
CREATE DATABASE IF NOT EXISTS crud_app;

-- Crear usuario (reemplaza 'user' y 'pass' por los valores deseados)
CREATE USER 'user'@'%' IDENTIFIED BY 'pass';

-- Otorgar permisos sobre la base de datos al usuario
GRANT ALL PRIVILEGES ON crud_app.* TO 'user'@'%';

-- Aplicar cambios
FLUSH PRIVILEGES;
