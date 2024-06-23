<?php
require_once 'db.php';

class Categoria {
    private $conn;

    public function __construct() {
        $database = new Database();
        $this->conn = $database->getConnection();
    }

    public function listarCategorias() {
        $query = "CALL usp_ListarCategorias()";
        $stmt = $this->conn->prepare($query);
        $stmt->execute();
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public function buscarCategoria($id) {
        $query = "CALL usp_BuscarCategoria(:id)";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(':id', $id);
        $stmt->execute();
        return $stmt->fetch(PDO::FETCH_ASSOC);
    }

    public function insertarCategoria($nombre) {
        $query = "CALL usp_InsertarCategoria(:nombre)";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(':nombre', $nombre);
        return $stmt->execute();
    }

    public function actualizarCategoria($id, $nombre) {
        $query = "CALL usp_ActualizarCategoria(:id, :nombre)";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(':id', $id);
        $stmt->bindParam(':nombre', $nombre);
        return $stmt->execute();
    }

    public function borrarCategoria($id) {
        $query = "CALL usp_BorrarCategoria(:id)";
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(':id', $id);
        return $stmt->execute();
    }
}
?>
