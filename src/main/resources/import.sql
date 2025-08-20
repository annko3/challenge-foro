INSERT INTO usuarios (nombre, email, contrasena) VALUES
('Ana Silva', 'ana.silva@email.com', '$2a$10$r3x4Jp7q8R9s0T1u2V3w4e5f6g7h8i9j0k1l2m3n4o5p6q7r8s9t0u1v'),
('Carlos Mendoza', 'carlos.mendoza@email.com', '$2a$10$s4t5U6v7W8x9Y0Z1a2b3c4d5e6f7g8h9i0j1k2l3m4n5o6p7q8r9s0t1u'),
('Laura González', 'laura.gonzalez@email.com', '$2a$10$t5u6V7w8X9y0Z1a2B3c4d5e6f7g8h9i0j1k2l3m4n5o6p7q8r9s0t1u');

INSERT INTO cursos (nombre, categoria) VALUES
('Spring Boot', 'Programación'),
('React', 'Frontend'),
('SQL', 'Bases de datos'),
('Docker', 'DevOps');

INSERT INTO topicos (titulo, mensaje, autor_id, curso_id, status) VALUES
('Problema con autenticación JWT', 'No puedo configurar la autenticación JWT en mi aplicación Spring Boot. ¿Alguien puede ayudarme?', 1, 1, 'NO_RESPONDIDO'),
('Error al renderizar componentes', 'Mis componentes React no se renderizan correctamente después de actualizar el estado.', 2, 2, 'NO_SOLUCIONADO'),
('Consulta SQL con múltiples joins', 'Necesito hacer una consulta que involucre tres tablas pero no obtengo los resultados esperados.', 3, 3, 'SOLUCIONADO');

INSERT INTO respuestas (mensaje, topico_id, autor_id, solucion) VALUES
('Para configurar JWT en Spring Boot, necesitas agregar las dependencias de spring-security y jjwt.', 1, 2, FALSE),
('Asegúrate de que estás usando correctamente el hook useState para actualizar el estado.', 2, 1, TRUE),
('¿Podrías compartir tu consulta actual para que podamos ayudarte mejor?', 3, 2, FALSE),
('Para múltiples joins, te recomiendo usar alias para cada tabla y verificar las relaciones.', 3, 1, TRUE);