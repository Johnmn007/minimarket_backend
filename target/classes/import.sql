-- import.sql (comentado temporalmente)
-- Este archivo será ejecutado por Hibernate cuando ddl-auto=create

/*
-- Insertar roles del sistema
INSERT INTO roles (nombre) VALUES ('ADMIN');
INSERT INTO roles (nombre) VALUES ('CAJERO'); 
INSERT INTO roles (nombre) VALUES ('ALMACENERO');

-- Crear usuario administrador por defecto (password: admin123)
INSERT INTO usuarios (username, password, activo) 
VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iS5zDfu3z3O9dV7lB7RyFcDTP0Oq', true);

-- Asignar rol ADMIN al usuario admin
INSERT INTO usuario_roles (usuario_id, rol_id)
SELECT u.id, r.id 
FROM usuarios u, roles r 
WHERE u.username = 'admin' AND r.nombre = 'ADMIN';
*/