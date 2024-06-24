<?php

// Cargar el archivo de conexión a la base de datos
require_once '../src/db.php';

// Obtener la ruta de la solicitud
$route = $_GET['route'] ?? '';

// Manejar las rutas
switch ($route) {
    case 'autores':
        if ($_SERVER['REQUEST_METHOD'] === 'GET') {
            require_once '../src/autor/listarAutor.php';
        } elseif ($_SERVER['REQUEST_METHOD'] === 'POST') {
            require_once '../src/autor/insertarAutor.php';
        } elseif ($_SERVER['REQUEST_METHOD'] === 'PUT') {
            require_once '../src/autor/actualizarAutor.php';
        } elseif ($_SERVER['REQUEST_METHOD'] === 'DELETE') {
            // Verificar si se proporcionó autor_id en la URL
            if (isset($_GET['autor_id'])) {
                require_once '../src/autor/eliminarAutor.php';
            } else {
                http_response_code(400); // Bad Request
                echo json_encode(array("message" => "Falta el parámetro autor_id para eliminar."));
            }
        } else {
            http_response_code(405); // Method Not Allowed
            echo json_encode(array("message" => "Método no permitido."));
        }
        break;
    case 'buscarAutor':
        if ($_SERVER['REQUEST_METHOD'] === 'GET') {
            require_once '../src/autor/buscarAutor.php';
        } else {
            http_response_code(405); // Method Not Allowed
            echo json_encode(array("message" => "Método no permitido."));
        }
        break;
    default:
        http_response_code(404); // Not Found
        echo json_encode(array("message" => "Endpoint no encontrado"));
        break;
}

error_reporting(E_ALL);
ini_set('display_errors', 1);
?>
