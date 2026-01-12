-- Insertar roles del sistema
-- INSERT INTO roles (nombre) 
-- VALUES ('ADMIN'), ('CAJERO'), ('ALMACENERO')
-- ON CONFLICT (nombre) DO NOTHING;

-- -- Crear usuario administrador por defecto (password: admin123)
-- -- Encriptado con BCrypt: $2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iS5zDfu3z3O9dV7lB7RyFcDTP0Oq
-- INSERT INTO usuarios (username, password, activo) 
-- VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iS5zDfu3z3O9dV7lB7RyFcDTP0Oq', true)
-- ON CONFLICT (username) DO NOTHING;

-- -- Asignar rol ADMIN al usuario admin
-- INSERT INTO usuario_roles (usuario_id, rol_id)
-- SELECT u.id, r.id 
-- FROM usuarios u, roles r 
-- WHERE u.username = 'admin' AND r.nombre = 'ADMIN'
-- ON CONFLICT DO NOTHING;

<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

-- import.sql - Ejecutado automáticamente por Hibernate cuando ddl-auto=create
-- Este archivo se ejecuta DESPUÉS de crear las tablas

-- -- Insertar roles del sistema
-- INSERT INTO roles (nombre) VALUES ('ADMIN');
-- INSERT INTO roles (nombre) VALUES ('CAJERO'); 
-- INSERT INTO roles (nombre) VALUES ('ALMACENERO');

-- -- Crear usuario administrador por defecto (password: admin123)
-- INSERT INTO usuarios (username, password, activo) 
-- VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iS5zDfu3z3O9dV7lB7RyFcDTP0Oq', true);

-- -- Asignar rol ADMIN al usuario admin
-- INSERT INTO usuario_roles (usuario_id, rol_id)
-- SELECT u.id, r.id 
-- FROM usuarios u, roles r 
-- WHERE u.username = 'admin' AND r.nombre = 'ADMIN';