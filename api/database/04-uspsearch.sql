DELIMITER $$

CREATE PROCEDURE usp_BuscarAutor(IN autor_id INT)
BEGIN
    SELECT  id,nombre,nacionalidad,fecha_nacimiento, biografia FROM autor 
	WHERE id = autor_id;
END$$


CREATE PROCEDURE usp_BuscarLibro(IN libro_id INT)
BEGIN
    SELECT id,titulo,publicacion,sinopsis FROM libro WHERE id = libro_id;
END$$

CREATE PROCEDURE usp_BuscarCategoria(IN categoria_id INT)
BEGIN
    SELECT id , nombre FROM categoria WHERE id = categoria_id;
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
    SELECT L.titulo 'Libro' ,A.nombre 'Autor' FROM libro_autor AS LA
	INNER JOIN libro AS L ON LA.libro_id = L.id
	INNER JOIN autor AS A ON LA.autor_id = A.id
    WHERE libro_id = libro_id AND autor_id =libro_id;
END$$

DELIMITER ;


