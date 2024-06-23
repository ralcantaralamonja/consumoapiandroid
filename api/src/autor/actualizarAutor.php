<?php

// Incluir el archivo de conexión a la base de datos
require_once 'db.php';

// Verificar si se proporcionaron los parámetros necesarios
if (!isset($_POST['autor_id']) || !isset($_POST['nombre']) || !isset($_POST['nacionalidad']) || !isset($_POST['fecha_nacimiento']) || !isset($_POST['biografia'])) {
    http_response_code(400); // Bad Request
    echo json_encode(array("message" => "Faltan parámetros."));
    exit();
}

$autor_id = $_POST['autor_id'];
$nombre = $_POST['nombre'];
$nacionalidad = $_POST['nacionalidad'];
$fecha_nacimiento = $_POST['fecha_nacimiento'];
$biografia = $_POST['biografia'];

// Query para llamar al procedimiento almacenado usp_ActualizarAutor
$query = "CALL usp_ActualizarAutor(:autor_id, :nombre, :nacionalidad, :fecha_nacimiento, :biografia)";

try {
    // Preparar la consulta
    $stmt = $pdo->prepare($query);

    // Bind de parámetros
    $stmt->bindParam(':autor_id', $autor_id, PDO::PARAM_INT);
    $stmt->bindParam(':nombre', $nombre, PDO::PARAM_STR);
    $stmt->bindParam(':nacionalidad', $nacionalidad, PDO::PARAM_STR);
    $stmt->bindParam(':fecha_nacimiento', $fecha_nacimiento, PDO::PARAM_STR);
    $stmt->bindParam(':biografia', $biografia, PDO::PARAM_STR);

    // Ejecutar la consulta
    $stmt->execute();

    // Retornar mensaje de éxito
    echo json_encode(array("message" => "Autor actualizado correctamente."));
} catch (PDOException $e) {
    // Manejo de errores si la consulta falla
    http_response_code(500); // Internal Server Error
    echo json_encode(array("message" => "No se pudo actualizar el autor. Error: " . $e->getMessage()));
}
