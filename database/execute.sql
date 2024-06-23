CREATE DATABASE Libreria;
USE LIBRERIA;

CREATE TABLE autor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    nacionalidad VARCHAR(50),
    fecha_nacimiento DATE,
    biografia VARCHAR(150)
);

CREATE TABLE libro (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    publicacion YEAR,
    sinopsis VARCHAR(150)
);

CREATE TABLE categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE libro_autor (
    libro_id INT,
    autor_id INT,
    PRIMARY KEY (libro_id, autor_id),
    FOREIGN KEY (libro_id) REFERENCES libro(id),
    FOREIGN KEY (autor_id) REFERENCES autor(id)
);

CREATE TABLE libro_categoria (
    libro_id INT,
    categoria_id INT,
    PRIMARY KEY (libro_id, categoria_id),
    FOREIGN KEY (libro_id) REFERENCES libro(id),
    FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);

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

DELIMITER $$

CREATE PROCEDURE usp_ListarAutores()
BEGIN
    SELECT id, nombre, nacionalidad, fecha_nacimiento, biografia FROM autor;
END$$

CREATE PROCEDURE usp_ListarLibros()
BEGIN
    SELECT id, titulo, publicacion, sinopsis FROM libro;
END$$

CREATE PROCEDURE usp_ListarCategorias()
BEGIN
    SELECT id, nombre FROM categoria;
END$$

CREATE PROCEDURE usp_ListarLibroCategoria()
BEGIN
    SELECT L.titulo 'Libro', C.nombre 'Categoria'
    FROM libro_categoria AS LC
    INNER JOIN libro AS L ON L.id = LC.libro_id
    INNER JOIN categoria AS C ON C.id = LC.categoria_id
    ORDER BY C.nombre;
END$$

CREATE PROCEDURE usp_ListarLibroAutor()
BEGIN
    SELECT L.titulo 'Libro', A.nombre 'Autor'
    FROM libro_autor AS LA
    INNER JOIN libro AS L ON LA.libro_id = L.id
    INNER JOIN autor AS A ON LA.autor_id = A.id;
END$$

DELIMITER $$

CREATE PROCEDURE usp_BuscarAutor(IN autor_id INT)
BEGIN
    SELECT id, nombre, nacionalidad, fecha_nacimiento, biografia
    FROM autor
    WHERE id = autor_id;
END$$

CREATE PROCEDURE usp_BuscarLibro(IN libro_id INT)
BEGIN
    SELECT id, titulo, publicacion, sinopsis
    FROM libro
    WHERE id = libro_id;
END$$

CREATE PROCEDURE usp_BuscarCategoria(IN categoria_id INT)
BEGIN
    SELECT id, nombre
    FROM categoria
    WHERE id = categoria_id;
END$$

CREATE PROCEDURE usp_BuscarLibroCategoria(IN idlibro INT)
BEGIN
    SELECT L.titulo AS 'Libro', C.nombre AS 'Categoria'
    FROM libro_categoria AS LC
    INNER JOIN libro AS L ON L.id = LC.libro_id
    INNER JOIN categoria AS C ON C.id = LC.categoria_id
    WHERE LC.libro_id = idlibro;
END$$

CREATE PROCEDURE usp_BuscarLibroAutor(IN libro_id INT)
BEGIN
    SELECT L.titulo 'Libro', A.nombre 'Autor'
    FROM libro_autor AS LA
    INNER JOIN libro AS L ON LA.libro_id = L.id
    INNER JOIN autor AS A ON LA.autor_id = A.id
    WHERE LA.libro_id = libro_id;
END$$

DELIMITER $$

CREATE PROCEDURE usp_InsertarAutor(
    IN in_nombre VARCHAR(100),
    IN in_nacionalidad VARCHAR(50),
    IN in_fecha_nacimiento DATE,
    IN in_biografia VARCHAR(150)
)
BEGIN
    INSERT INTO autor (nombre, nacionalidad, fecha_nacimiento, biografia) 
    VALUES (in_nombre, in_nacionalidad, in_fecha_nacimiento, in_biografia);
END$$

CREATE PROCEDURE usp_InsertarLibro(
    IN in_titulo VARCHAR(200),
    IN in_publicacion YEAR,
    IN in_sinopsis VARCHAR(150)
)
BEGIN
    INSERT INTO libro (titulo, publicacion, sinopsis) 
    VALUES (in_titulo, in_publicacion, in_sinopsis);
END$$

CREATE PROCEDURE usp_InsertarCategoria(
    IN in_nombre VARCHAR(50)
)
BEGIN
    INSERT INTO categoria (nombre) 
    VALUES (in_nombre);
END$$

CREATE PROCEDURE usp_InsertarLibroCategoria(
    IN in_libro_id INT,
    IN in_categoria_id INT
)
BEGIN
    INSERT INTO libro_categoria (libro_id, categoria_id) 
    VALUES (in_libro_id, in_categoria_id);
END$$

CREATE PROCEDURE usp_InsertarLibroAutor(
    IN in_libro_id INT,
    IN in_autor_id INT
)
BEGIN
    INSERT INTO libro_autor (libro_id, autor_id) 
    VALUES (in_libro_id, in_autor_id);
END$$

DELIMITER $$

CREATE PROCEDURE usp_ActualizarAutor(
    IN in_autor_id INT,
    IN in_nombre VARCHAR(100),
    IN in_nacionalidad VARCHAR(50),
    IN in_fecha_nacimiento DATE,
    IN in_biografia VARCHAR(150)
)
BEGIN
    UPDATE autor 
    SET nombre = in_nombre, nacionalidad = in_nacionalidad, fecha_nacimiento = in_fecha_nacimiento, biografia = in_biografia 
    WHERE id = in_autor_id;
END$$

CREATE PROCEDURE usp_ActualizarLibro(
    IN in_libro_id INT,
    IN in_titulo VARCHAR(200),
    IN in_publicacion YEAR,
    IN in_sinopsis VARCHAR(150)
)
BEGIN
    UPDATE libro 
    SET titulo = in_titulo, publicacion = in_publicacion, sinopsis = in_sinopsis 
    WHERE id = in_libro_id;
END$$

CREATE PROCEDURE usp_ActualizarCategoria(
    IN in_categoria_id INT,
    IN in_nombre VARCHAR(50)
)
BEGIN
    UPDATE categoria 
    SET nombre = in_nombre 
    WHERE id = in_categoria_id;
END$$

CREATE PROCEDURE usp_ActualizarLibroCategoria(
    IN in_libro_id INT,
    IN in_categoria_id INT,
    IN in_nuevo_libro_id INT,
    IN in_nuevo_categoria_id INT
)
BEGIN
    UPDATE libro_categoria 
    SET libro_id = in_nuevo_libro_id, categoria_id = in_nuevo_categoria_id 
    WHERE libro_id = in_libro_id AND categoria_id = in_categoria_id;
END$$

CREATE PROCEDURE usp_ActualizarLibroAutor(
    IN in_libro_id INT,
    IN in_autor_id INT,
    IN in_nuevo_libro_id INT,
    IN in_nuevo_autor_id INT
)
BEGIN
    UPDATE libro_autor 
    SET libro_id = in_nuevo_libro_id, autor_id = in_nuevo_autor_id 
    WHERE libro_id = in_libro_id AND autor_id = in_autor_id;
END$$

DELIMITER $$

CREATE PROCEDURE usp_BorrarAutor(IN in_autor_id INT)
BEGIN
    DELETE FROM autor WHERE id = in_autor_id;
END$$

CREATE PROCEDURE usp_BorrarLibro(IN in_libro_id INT)
BEGIN
    DELETE FROM libro WHERE id = in_libro_id;
END$$

CREATE PROCEDURE usp_BorrarCategoria(IN in_categoria_id INT)
BEGIN
    DELETE FROM categoria WHERE id = in_categoria_id;
END$$

CREATE PROCEDURE usp_BorrarLibroCategoria(
    IN in_libro_id INT, 
    IN in_categoria_id INT
)
BEGIN
    DELETE FROM libro_categoria WHERE libro_id = in_libro_id AND categoria_id = in_categoria_id;
END$$

CREATE PROCEDURE usp_BorrarLibroAutor(
    IN in_libro_id INT, 
    IN in_autor_id INT
)
BEGIN
    DELETE FROM libro_autor WHERE libro_id = in_libro_id AND autor_id = in_autor_id;
END$$

DELIMITER ;
