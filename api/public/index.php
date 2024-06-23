<?php

// Cargar el archivo de conexión a la base de datos
require_once '../src/db.php';

// Manejar la solicitud GET para autores
if ($_SERVER['REQUEST_METHOD'] === 'GET' && isset($_GET['route']) && $_GET['route'] === 'autores') {
    // Incluir el archivo autor.php para manejar la lógica de autores
    require_once '../src/autor/listarAutor.php';
    require_once '../src/autor/buscarAutor.php';
} else {
    // Si no se encuentra ninguna ruta válida, devolver un error 404
    http_response_code(404);
    echo json_encode(array("message" => "Endpoint no encontrado"));
}

error_reporting(E_ALL);
ini_set('display_errors', 1);
