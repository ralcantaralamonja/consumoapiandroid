<?php
require_once 'db.php';

class Libro {
    private $conn;

    public function __construct() {
        $database = new Database();
        $this->conn = $database->getConnection();
    }

    public function listarLibros() {
        $query = "CALL usp_ListarLibros()";
        $stmt = $this->conn->prepare($query);
        $stmt->execute();
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public function buscarLibro($id) {
        $query = "CALL usp_BuscarLibro(:id)";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(':id', $id);
        $stmt->execute();
        return $stmt->fetch(PDO::FETCH_ASSOC);
    }

    public function insertarLibro($titulo, $publicacion, $sinopsis) {
        $query = "CALL usp_InsertarLibro(:titulo, :publicacion, :sinopsis)";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(':titulo', $titulo);
        $stmt->bindParam(':publicacion', $publicacion);
        $stmt->bindParam(':sinopsis', $sinopsis);
        return $stmt->execute();
    }

    public function actualizarLibro($id, $titulo, $publicacion, $sinopsis) {
        $query = "CALL usp_ActualizarLibro(:id, :titulo, :publicacion, :sinopsis)";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(':id', $id);
        $stmt->bindParam(':titulo', $titulo);
        $stmt->bindParam(':publicacion', $publicacion);
        $stmt->bindParam(':sinopsis', $sinopsis);
        return $stmt->execute();
    }

    public function borrarLibro($id) {
        $query = "CALL usp_BorrarLibro(:id)";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(':id', $id);
        return $stmt->execute();
    }
}
?>
