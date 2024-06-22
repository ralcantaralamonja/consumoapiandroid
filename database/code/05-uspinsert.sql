DELIMITER $$

CREATE PROCEDURE usp_InsertarAutor(
    IN nombre VARCHAR(100),
    IN nacionalidad VARCHAR(50),
    IN fecha_nacimiento DATE,
    IN biografia varchar(150)
)
BEGIN
    INSERT INTO autor (nombre, nacionalidad, fecha_nacimiento, biografia) 
    VALUES (nombre, nacionalidad, fecha_nacimiento, biografia);
END$$

CREATE PROCEDURE usp_InsertarLibro(
    IN titulo VARCHAR(200),
    IN publicacion YEAR,
    IN sinopsis varchar(150)
)
BEGIN
    INSERT INTO libro (titulo, publicacion, sinopsis) 
    VALUES (titulo, publicacion, sinopsis);
END$$

CREATE PROCEDURE usp_InsertarCategoria(
    IN nombre VARCHAR(50)
)
BEGIN
    INSERT INTO categoria (nombre) 
    VALUES (nombre);
END$$

CREATE PROCEDURE usp_InsertarLibroCategoria(
    IN libro_id INT,
    IN categoria_id INT
)
BEGIN
    INSERT INTO libro_categoria (libro_id, categoria_id) 
    VALUES (libro_id, categoria_id);
END$$

CREATE PROCEDURE usp_InsertarLibroAutor(
    IN libro_id INT,
    IN autor_id INT
)
BEGIN
    INSERT INTO libro_autor (libro_id, autor_id) 
    VALUES (libro_id, autor_id);
END$$

DELIMITER ;

