<?php

// Configuración de la conexión a la base de datos
$host = 'localhost'; // o tu host
$dbname = 'libreria'; // nombre de tu base de datos
$username = 'root'; // usuario de la base de datos
$password = '123'; // contraseña de la base de datos

// Configurar la conexión PDO
$dsn = "mysql:host=$host;dbname=$dbname;charset=utf8mb4";

try {
    $pdo = new PDO($dsn, $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    echo "Error de conexión: " . $e->getMessage();
    exit;
}
