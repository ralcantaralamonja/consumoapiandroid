
INSERT INTO autor (nombre, nacionalidad, fecha_nacimiento, biografia) VALUES 
('Gabriel García Márquez', 'Colombiana', '1927-03-06', 'Gabriel García Márquez fue un novelista, cuentista, guionista y periodista colombiano.'),
('Isabel Allende', 'Chilena', '1942-08-02', 'Isabel Allende es una escritora chilena que ha vendido más de 70 millones de copias de sus libros.'),
('Mario Vargas Llosa', 'Peruana', '1936-03-28', 'Mario Vargas Llosa es un escritor, político y periodista peruano, ganador del Premio Nobel de Literatura.'),
('Julio Cortázar', 'Argentina', '1914-08-26', 'Julio Cortázar fue un escritor, traductor e intelectual argentino.'),
('Jorge Luis Borges', 'Argentina', '1899-08-24', 'Jorge Luis Borges fue un escritor, poeta, ensayista y bibliotecario argentino.');


INSERT INTO libro (titulo, publicacion, sinopsis) VALUES 
('Cien Años de Soledad', 1967, 'Cien Años de Soledad es una novela del escritor colombiano Gabriel García Márquez.'),
('La Casa de los Espíritus', 1982, 'La Casa de los Espíritus es la primera novela de la escritora chilena Isabel Allende.'),
('La Ciudad y los Perros', 1963, 'La Ciudad y los Perros es una novela del escritor peruano Mario Vargas Llosa.'),
('Rayuela', 1963, 'Rayuela es una novela del escritor argentino Julio Cortázar.'),
('El Aleph', 1949, 'El Aleph es un libro de cuentos del escritor argentino Jorge Luis Borges.');


INSERT INTO categoria (nombre) VALUES 
('Realismo Mágico'),
('Novela Histórica'),
('Ficción'),
('Literatura Latinoamericana'),
('Cuento');


INSERT INTO libro_autor (libro_id, autor_id) VALUES 
(1, 1), -- Cien Años de Soledad - Gabriel García Márquez
(2, 2), -- La Casa de los Espíritus - Isabel Allende
(3, 3), -- La Ciudad y los Perros - Mario Vargas Llosa
(4, 4), -- Rayuela - Julio Cortázar
(5, 5); -- El Aleph - Jorge Luis Borges

INSERT INTO libro_categoria (libro_id, categoria_id) VALUES 
(1, 1), -- Cien Años de Soledad - Realismo Mágico
(1, 4), -- Cien Años de Soledad - Literatura Latinoamericana
(2, 2), -- La Casa de los Espíritus - Novela Histórica
(2, 4), -- La Casa de los Espíritus - Literatura Latinoamericana
(3, 3), -- La Ciudad y los Perros - Ficción
(3, 4), -- La Ciudad y los Perros - Literatura Latinoamericana
(4, 3), -- Rayuela - Ficción
(4, 4), -- Rayuela - Literatura Latinoamericana
(5, 5); -- El Aleph - Cuento
