<?php

// Incluir el archivo de conexión a la base de datos
require_once '../db.php';

// Query para llamar al procedimiento almacenado usp_ListarAutores
$query = "CALL usp_ListarAutores()";

try {
    // Preparar la consulta
    $stmt = $pdo->prepare($query);

    // Ejecutar la consulta
    $stmt->execute();

    // Obtener los resultados como un array asociativo
    $autores = $stmt->fetchAll(PDO::FETCH_ASSOC);

    // Retornar los resultados como JSON
    header('Content-Type: application/json');
    echo json_encode($autores);
} catch (PDOException $e) {
    // Manejo de errores si la consulta falla
    http_response_code(500); // Internal Server Error
    echo json_encode(array("message" => "No se pudo listar los autores. Error: " . $e->getMessage()));
}
