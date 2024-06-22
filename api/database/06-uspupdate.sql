DELIMITER $$

CREATE PROCEDURE usp_ActualizarAutor(
    IN autor_id INT,
    IN nombre VARCHAR(100),
    IN nacionalidad VARCHAR(50),
    IN fecha_nacimiento DATE,
    IN biografia varchar(150)
)
BEGIN
    UPDATE autor 
    SET nombre = nombre, nacionalidad = nacionalidad, fecha_nacimiento = fecha_nacimiento, biografia = biografia 
    WHERE id = autor_id;
END$$

CREATE PROCEDURE usp_ActualizarLibro(
    IN libro_id INT,
    IN titulo VARCHAR(200),
    IN publicacion YEAR,
    IN sinopsis varchar(150)
)
BEGIN
    UPDATE libro 
    SET titulo = titulo, publicacion = publicacion, sinopsis = sinopsis 
    WHERE id = libro_id;
END$$

CREATE PROCEDURE usp_ActualizarCategoria(
    IN categoria_id INT,
    IN nombre VARCHAR(50)
)
BEGIN
    UPDATE categoria 
    SET nombre = nombre 
    WHERE id = categoria_id;
END$$

CREATE PROCEDURE usp_ActualizarLibroCategoria(
    IN libro_id INT,
    IN categoria_id INT,
    IN nuevo_libro_id INT,
    IN nuevo_categoria_id INT
)
BEGIN
    UPDATE libro_categoria 
    SET libro_id = nuevo_libro_id, categoria_id = nuevo_categoria_id 
    WHERE libro_id = libro_id AND categoria_id = categoria_id;
END$$

CREATE PROCEDURE usp_ActualizarLibroAutor(
    IN libro_id INT,
    IN autor_id INT,
    IN nuevo_libro_id INT,
    IN nuevo_autor_id INT
)
BEGIN
    UPDATE libro_autor 
    SET libro_id = nuevo_libro_id, autor_id = nuevo_autor_id 
    WHERE libro_id = libro_id AND autor_id = autor_id;
END$$

DELIMITER ;
