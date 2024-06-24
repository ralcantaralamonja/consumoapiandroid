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

// Consulta SQL para seleccionar todos los libros
$query = "SELECT * FROM libros";
$result = mysqli_query($con, $query);

if (!$result) {
    $response['error'] = true;
    $response['mensaje'] = "Error al obtener la lista de libros: " . mysqli_error($con);
} else {
    $libros = array();

    while ($row = mysqli_fetch_assoc($result)) {
        $libros[] = $row;
    }

    $response['error'] = false;
    $response['libros'] = $libros;
}

// Enviamos la respuesta en formato JSON
header('Content-Type: application/json');
echo json_encode($response);

// Cerramos la conexión
mysqli_close($con);
?>
