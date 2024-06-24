<?php

// Incluir el archivo de conexión a la base de datos
require_once '../db.php';

// Verificar si la solicitud es de tipo POST
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Leer los datos JSON de la solicitud
    $input = file_get_contents('php://input');
    $data = json_decode($input, true);

    // Verificar si se proporcionaron todos los datos necesarios
    if (isset($data['nombre'], $data['nacionalidad'], $data['fecha_nacimiento'], $data['biografia'])) {
        $nombre = $data['nombre'];
        $nacionalidad = $data['nacionalidad'];
        $fecha_nacimiento = $data['fecha_nacimiento'];
        $biografia = $data['biografia'];

        // Query para llamar al procedimiento almacenado usp_InsertarAutor
        $query = "CALL usp_InsertarAutor(:nombre, :nacionalidad, :fecha_nacimiento, :biografia)";

        try {
            // Preparar la consulta
            $stmt = $pdo->prepare($query);

            // Bind de parámetros
            $stmt->bindParam(':nombre', $nombre, PDO::PARAM_STR);
            $stmt->bindParam(':nacionalidad', $nacionalidad, PDO::PARAM_STR);
            $stmt->bindParam(':fecha_nacimiento', $fecha_nacimiento, PDO::PARAM_STR);
            $stmt->bindParam(':biografia', $biografia, PDO::PARAM_STR);

            // Ejecutar la consulta
            $stmt->execute();

            // Retornar mensaje de éxito
            http_response_code(201); // Created
            echo json_encode(array("message" => "Autor creado correctamente."));
        } catch (PDOException $e) {
            // Manejo de errores si la consulta falla
            http_response_code(500); // Internal Server Error
            echo json_encode(array("message" => "No se pudo crear el autor. Error: " . $e->getMessage()));
        }
    } else {
        // Si faltan parámetros
        http_response_code(400); // Bad Request
        echo json_encode(array("message" => "Faltan parámetros."));
    }
} else {
    // Si no es una solicitud POST
    http_response_code(405); // Method Not Allowed
    echo json_encode(array("message" => "Método no permitido."));
}

error_reporting(E_ALL);
ini_set('display_errors', 1);
