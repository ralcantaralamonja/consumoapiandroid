<?php
require_once 'db.php';

class LibroCategoria {
    private $conn;

    public function __construct() {
        $database = new Database();
        $this->conn = $database->getConnection();
    }

    public function listarLibrosCategorias() {
        $query = "CALL usp_ListarLibroCategoria()";
        $stmt = $this->conn->prepare($query);
        $stmt->execute();
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public function insertarLibroCategoria($libro_id, $categoria_id) {
        $query = "CALL usp_InsertarLibroCategoria(:libro_id, :categoria_id)";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(':libro_id', $libro_id);
        $stmt->bindParam(':categoria_id', $categoria_id);
        return $stmt->execute();
    }

    public function actualizarLibroCategoria($libro_id, $categoria_id, $nuevo_libro_id, $nueva_categoria_id) {
        $query = "CALL usp_ActualizarLibroCategoria(:libro_id, :categoria_id, :nuevo_libro_id, :nueva_categoria_id)";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(':libro_id', $libro_id);
        $stmt->bindParam(':categoria_id', $categoria_id);
        $stmt->bindParam(':nuevo_libro_id', $nuevo_libro_id);
        $stmt->bindParam(':nueva_categoria_id', $nueva_categoria_id);
        return $stmt->execute();
    }

    public function borrarLibroCategoria($libro_id, $categoria_id) {
        $query = "CALL usp_BorrarLibroCategoria(:libro_id, :categoria_id)";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(':libro_id', $libro_id);
        $stmt->bindParam(':categoria_id', $categoria_id);
        return $stmt->execute();
    }
}
?>
