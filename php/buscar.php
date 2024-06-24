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

// Recuperar el ID del libro a buscar desde JSON
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
if ($data === null) {
    $response['error'] = true;
    $response['mensaje'] = "Error al decodificar el JSON";
    echo json_encode($response);
    exit;
}

// Obtener el ID del libro desde el array
$id = $data['id'];

// Consulta SQL para buscar el libro por su ID
$query = "SELECT * FROM libros WHERE id = $id";

$result = mysqli_query($con, $query);

if ($result) {
    if (mysqli_num_rows($result) > 0) {
        // El libro fue encontrado, obtener los datos
        $libro = mysqli_fetch_assoc($result);
        $response['error'] = false;
        $response['libro'] = $libro;
    } else {
        // No se encontró ningún libro con el ID especificado
        $response['error'] = true;
        $response['mensaje'] = "No se encontró ningún libro con el ID: $id";
    }
} else {
    // Error en la consulta SQL
    $response['error'] = true;
    $response['mensaje'] = "Error al buscar el libro: " . mysqli_error($con);
}

// Enviamos la respuesta en formato JSON
header('Content-Type: application/json');
echo json_encode($response);

// Cerramos la conexión
mysqli_close($con);
?>
