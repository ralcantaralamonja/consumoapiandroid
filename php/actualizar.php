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

// Recuperamos los datos que el usuario desea actualizar
$id = $_REQUEST['id'];
$titulo = $_REQUEST['titulo'];
$autor = $_REQUEST['autor'];
$anio_publicacion = $_REQUEST['anio'];

// Verificar si se proporcionaron los parámetros necesarios
if (!isset($id) || !isset($titulo) || !isset($autor) || !isset($anio_publicacion)) {
    http_response_code(400); // Bad Request
    echo json_encode(array("error" => true, "mensaje" => "Faltan parámetros."));
    exit();
}

try {
    // Preparamos la consulta SQL
    $query = "UPDATE libros SET titulo = '$titulo', autor = '$autor', año_publicacion = $anio_publicacion WHERE id = $id";

    // Ejecutamos la consulta
    if (mysqli_query($con, $query)) {
        $response['error'] = false;
        $response['mensaje'] = "Libro actualizado correctamente";
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
