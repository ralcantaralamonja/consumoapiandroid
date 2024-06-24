<?php
$server = 'localhost';
$user = 'root';
$password = '123';
$database = 'biblioteca';

// Establecemos conexión
$con = mysqli_connect($server, $user, $password, $database);

// Verificar conexión
if (!$con) {
    die("Connection failed: " . mysqli_connect_error());
}

// Recuperar el ID del libro a eliminar desde JSON
$json_data = file_get_contents('php://input');

if (empty($json_data)) {
    $response['error'] = true;
    $response['mensaje'] = "No se recibieron datos JSON";
    echo json_encode($response);
    exit;
}

// Convertir el JSON recibido a un array asociativo de PHP
$data = json_decode($json_data, true);

// Verificar si se pudo decodificar el JSON correctamente
if ($data === null || !isset($data['id'])) {
    $response['error'] = true;
    $response['mensaje'] = "Error: Se requiere proporcionar el ID del libro a eliminar";
    echo json_encode($response);
    exit;
}

// Obtener el ID del libro desde el array
$id = $data['id'];

// Consulta SQL para borrar el libro por ID
$query = "DELETE FROM libros WHERE id = $id";

if (mysqli_query($con, $query)) {
    $response['error'] = false;
    $response['mensaje'] = "Libro eliminado correctamente";
} else {
    $response['error'] = true;
    $response['mensaje'] = "Error al eliminar el libro: " . mysqli_error($con);
}

// Enviamos la respuesta en formato JSON
header('Content-Type: application/json');
echo json_encode($response);

// Cerramos la conexión
mysqli_close($con);
?>
