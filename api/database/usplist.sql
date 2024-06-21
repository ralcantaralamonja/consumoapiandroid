DELIMITER $$

CREATE PROCEDURE usp_ListarAutores()
BEGIN
    SELECT  id,nombre,nacionalidad,fecha_nacimiento, biografia FROM autor;
END$$

CREATE PROCEDURE usp_ListarLibros()
BEGIN
    SELECT  id,titulo,publicacion,sinopsis FROM libro;
END$$

CREATE PROCEDURE usp_ListarCategorias()
BEGIN
    SELECT  id , nombre FROM categoria;
END$$

CREATE PROCEDURE usp_ListarCategoria()
BEGIN
 SELECT L.titulo 'Libro',C.nombre 'Categoria' FROM 
libro_categoria AS LC
INNER JOIN libro AS L ON L.id = LC.libro_id
INNER JOIN categoria AS C ON C.id = LC.categoria_id
order by C.nombre;
END$$

CREATE PROCEDURE usp_ListarLibroAutor()
BEGIN
    SELECT L.titulo 'Libro' ,A.nombre 'Autor' FROM 
	libro_autor AS LA
	INNER JOIN libro AS L ON LA.libro_id = L.id
	INNER JOIN autor AS A ON LA.autor_id = A.id;
END$$

DELIMITER ;


 