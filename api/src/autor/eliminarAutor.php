<?php

// Incluir el archivo de conexión a la base de datos
require_once '../db.php';

// Verificar si se proporcionó el parámetro autor_id en la URL
$autor_id = $_GET['autor_id'] ?? null;

if (!$autor_id) {
    http_response_code(400); // Bad Request
    echo json_encode(array("message" => "Falta el parámetro autor_id."));
    exit();
}

// Query para llamar al procedimiento almacenado usp_BorrarAutor
$query = "CALL usp_BorrarAutor(:autor_id)";

try {
    // Preparar la consulta
    $stmt = $pdo->prepare($query);

    // Bind de parámetros
    $stmt->bindParam(':autor_id', $autor_id, PDO::PARAM_INT);

    // Ejecutar la consulta
    $stmt->execute();

    // Retornar mensaje de éxito
    echo json_encode(array("message" => "Autor eliminado correctamente."));
} catch (PDOException $e) {
    // Manejo de errores si la consulta falla
    http_response_code(500); // Internal Server Error
    echo json_encode(array("message" => "No se pudo eliminar el autor. Error: " . $e->getMessage()));
}
?>
