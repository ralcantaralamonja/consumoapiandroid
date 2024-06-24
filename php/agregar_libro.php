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

// Recuperamos los datos que el usuario desea agregar
$titulo = $_REQUEST['titulo'];
$autor = $_REQUEST['autor'];
$anio_publicacion = $_REQUEST['anio']; // Cambiado a 'anio'

try {
    // Preparamos la consulta SQL
    $query = "INSERT INTO libros (titulo, autor, año_publicacion) VALUES ('$titulo', '$autor', $anio_publicacion);";

    // Ejecutamos la consulta
    if (mysqli_query($con, $query)) {
        $response['error'] = false;
        $response['mensaje'] = "Libro registrado correctamente";
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
