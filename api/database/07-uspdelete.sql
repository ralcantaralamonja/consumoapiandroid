DELIMITER $$

CREATE PROCEDURE BorrarAutor(IN autor_id INT)
BEGIN
    DELETE FROM autor WHERE id = autor_id;
END$$

CREATE PROCEDURE BorrarLibro(IN libro_id INT)
BEGIN
    DELETE FROM libro WHERE id = libro_id;
END$$

CREATE PROCEDURE BorrarCategoria(IN categoria_id INT)
BEGIN
    DELETE FROM categoria WHERE id = categoria_id;
END$$

CREATE PROCEDURE BorrarLibroCategoria(IN libro_id INT, IN categoria_id INT)
BEGIN
    DELETE FROM libro_categoria WHERE libro_id = libro_id AND categoria_id = categoria_id;
END$$

CREATE PROCEDURE BorrarLibroAutor(IN libro_id INT, IN autor_id INT)
BEGIN
    DELETE FROM libro_autor WHERE libro_id = libro_id AND autor_id = autor_id;
END$$

DELIMITER ;
