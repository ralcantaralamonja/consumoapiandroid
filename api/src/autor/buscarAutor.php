<?php

// Incluir el archivo de conexión a la base de datos
require_once '../db.php';

// Verificar si se proporcionó el parámetro autor_id en la URL
if (!isset($_GET['autor_id'])) {
    http_response_code(400); // Bad Request
    echo json_encode(array("message" => "Falta el parámetro autor_id."));
    exit();
}

$autor_id = $_GET['autor_id'];

// Query para llamar al procedimiento almacenado usp_BuscarAutor
$query = "CALL usp_BuscarAutor(:autor_id)";

try {
    // Preparar la consulta
    $stmt = $pdo->prepare($query);

    // Bind de parámetros
    $stmt->bindParam(':autor_id', $autor_id, PDO::PARAM_INT);

    // Ejecutar la consulta
    $stmt->execute();

    // Obtener el resultado
    $autor = $stmt->fetch(PDO::FETCH_ASSOC);

    if ($autor) {
        // Retornar el resultado como JSON
        header('Content-Type: application/json');
        echo json_encode($autor);
    } else {
        // Si no se encontró el autor
        http_response_code(404); // Not Found
        echo json_encode(array("message" => "Autor no encontrado."));
    }
} catch (PDOException $e) {
    // Manejo de errores si la consulta falla
    http_response_code(500); // Internal Server Error
    echo json_encode(array("message" => "No se pudo buscar el autor. Error: " . $e->getMessage()));
}
