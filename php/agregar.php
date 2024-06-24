<?php
$server = 'localhost';
$user = 'root';
$password = '123';
$database = 'biblioteca';

// Establecer conexión
$con = mysqli_connect($server, $user, $password, $database);

// Verificar conexión
if (!$con) {
    die("Connection failed: " . mysqli_connect_error());
}

// Recuperar datos del libro desde el cuerpo del JSON
$data = json_decode(file_get_contents('php://input'), true);

$titulo = $data['titulo'] ?? '';
$autor = $data['autor'] ?? '';
$anio = $data['anio'] ?? '';

// Validar que todos los campos necesarios están presentes
if (empty($titulo) || empty($autor) || empty($anio)) {
    $response['error'] = true;
    $response['mensaje'] = "Todos los campos (titulo, autor, anio) son obligatorios";
    echo json_encode($response);
    exit;
}

// Consulta SQL para insertar el libro
$query = "INSERT INTO libros (titulo, autor, anio) VALUES ('$titulo', '$autor', $anio)";

$response = array();

// Ejecutar la consulta SQL
if (mysqli_query($con, $query)) {
    $response['error'] = false;
    $response['mensaje'] = "Libro agregado correctamente";
} else {
    $response['error'] = true;
    $response['mensaje'] = "Error al agregar el libro: " . mysqli_error($con);
}

// Enviar respuesta en formato JSON
header('Content-Type: application/json');
echo json_encode($response);

// Cerrar conexión
mysqli_close($con);
?>
