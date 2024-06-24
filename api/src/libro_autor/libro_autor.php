<?php
require_once 'db.php';

class LibroAutor {
    private $conn;

    public function __construct() {
        $database = new Database();
        $this->conn = $database->getConnection();
    }

    public function listarLibrosAutores() {
        $query = "CALL usp_ListarLibroAutor()";
        $stmt = $this->conn->prepare($query);
        $stmt->execute();
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public function insertarLibroAutor($libro_id, $autor_id) {
        $query = "CALL usp_InsertarLibroAutor(:libro_id, :autor_id)";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(':libro_id', $libro_id);
        $stmt->bindParam(':autor_id', $autor_id);
        return $stmt->execute();
    }

    public function actualizarLibroAutor($libro_id, $autor_id, $nuevo_libro_id, $nuevo_autor_id) {
        $query = "CALL usp_ActualizarLibroAutor(:libro_id, :autor_id, :nuevo_libro_id, :nuevo_autor_id)";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(':libro_id', $libro_id);
        $stmt->bindParam(':autor_id', $autor_id);
        $stmt->bindParam(':nuevo_libro_id', $nuevo_libro_id);
        $stmt->bindParam(':nuevo_autor_id', $nuevo_autor_id);
        return $stmt->execute();
    }

    public function borrarLibroAutor($libro_id, $autor_id) {
        $query = "CALL usp_BorrarLibroAutor(:libro_id, :autor_id)";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(':libro_id', $libro_id);
        $stmt->bindParam(':autor_id', $autor_id);
        return $stmt->execute();
    }
}
?>
