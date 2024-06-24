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

// Recuperamos el ID del libro que se desea borrar
$id = $_REQUEST['id'];

// Verificar si se proporcionó el parámetro necesario
if (!isset($id)) {
    http_response_code(400); // Bad Request
    echo json_encode(array("error" => true, "mensaje" => "Falta el parámetro id."));
    exit();
}

try {
    // Preparamos la consulta SQL
    $query = "DELETE FROM libros WHERE id = $id";

    // Ejecutamos la consulta
    if (mysqli_query($con, $query)) {
        $response['error'] = false;
        $response['mensaje'] = "Libro borrado correctamente";
    } else {
        $response['error'] = true;
        $response['mensaje'] = "Error: " . mysqli_error($con);
    }
} catch (Exception $e) {
    $response['error'] = true;
    $response['mensaje'] = "Exception: " . $e->getMessage();
}

// Enviamos la respuesta en formato JSON
header('Content-Type: application/json');
echo json_encode($response);

// Cerramos la conexión
mysqli_close($con);
?>
