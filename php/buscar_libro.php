<?php
$server = 'localhost';
$user = 'root';
$password = '123';
$database = 'biblioteca';

// Conexión a la base de datos
$con = mysqli_connect($server, $user, $password, $database);

// Verificar conexión
if (!$con) {
    die('Error de conexión: ' . mysqli_connect_error());
}

// Verificar si se proporcionó el parámetro id
if (!isset($_GET['id'])) {
    http_response_code(400); // Bad Request
    echo json_encode(array("message" => "Falta el parámetro id."));
    exit();
}

$id = $_GET['id'];

// Consulta SQL para seleccionar el libro por ID
$rs = $con->query("SELECT * FROM libros WHERE id = $id");

// Verificar si hubo algún error en la consulta
if (!$rs) {
    die('Error en la consulta: ' . mysqli_error($con));
}

// Verificar si se encontró el libro
if ($rs->num_rows == 0) {
    http_response_code(404); // Not Found
    echo json_encode(array("message" => "Libro no encontrado."));
    exit();
}

// Obtener el libro
$libro = $rs->fetch_assoc();

// Convertir el libro a formato JSON
$json = json_encode($libro, JSON_UNESCAPED_UNICODE);

// Verificar si hubo algún error al convertir a JSON
if (!$json) {
    die('Error al convertir a JSON');
}

// Mostrar el JSON resultante
header('Content-Type: application/json');
echo $json;

// Cerrar la conexión
mysqli_close($con);
?>
