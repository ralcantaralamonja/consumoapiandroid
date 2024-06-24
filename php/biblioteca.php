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

// Consulta SQL para seleccionar todos los libros
$rs = $con->query("SELECT * FROM libros");

// Verificar si hubo algún error en la consulta
if (!$rs) {
    die('Error en la consulta: ' . mysqli_error($con));
}

// Inicializar un array para almacenar los libros
$libros = array();

// Recorrer los resultados y almacenarlos en el array
while ($row = $rs->fetch_assoc()) {
    $libros[] = $row;
}

// Convertir el array de libros a formato JSON
$json = json_encode($libros, JSON_UNESCAPED_UNICODE);

// Verificar si hubo algún error al convertir a JSON
if (!$json) {
    die('Error al convertir a JSON');
}

// Mostrar el JSON resultante
echo $json;

// Cerrar la conexión
mysqli_close($con);
