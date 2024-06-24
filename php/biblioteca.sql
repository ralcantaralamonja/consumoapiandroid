
CREATE DATABASE biblioteca;

USE biblioteca;

CREATE TABLE libros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    año_publicacion INT
);

INSERT INTO libros (titulo, autor, año_publicacion) VALUES
('Don Quijote de la Mancha', 'Miguel de Cervantes', 1605),
('Orgullo y prejuicio', 'Jane Austen', 1813),
('Moby Dick', 'Herman Melville', 1851),
('Cien años de soledad', 'Gabriel García Márquez', 1967),
('1984', 'George Orwell', 1949),
('El Gran Gatsby', 'F. Scott Fitzgerald', 1925),
('En busca del tiempo perdido', 'Marcel Proust', 1913),
('Ulises', 'James Joyce', 1922),
('Crimen y castigo', 'Fiódor Dostoyevski', 1866),
('El señor de los anillos', 'J.R.R. Tolkien', 1954);