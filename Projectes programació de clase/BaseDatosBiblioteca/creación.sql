Drop DATABASE IF EXISTS Bibliotecas;

CREATE DATABASE IF NOT EXISTS Bibliotecas;

Use Bibliotecas;

Create Table Adminitrasores(
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    DNI VARCHAR(9) NOT NULL UNIQUE,
    carrec VARCHAR(100) NOT NULL
);

Create Table Socis(
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    DNI VARCHAR(9) NOT NULL UNIQUE
);

Create Table Llibre(
    id INT PRIMARY KEY AUTO_INCREMENT,
    titol VARCHAR(50) NOT NULL,
    autor VARCHAR(50) NOT NULL ,
    exemplars INT NOT NULL
);

Create Table Revista(
    id INT PRIMARY KEY AUTO_INCREMENT,
    titol VARCHAR(50) NOT NULL,
    data DATE NOT NULL ,
    exemplars INT NOT NULL
);

Create Table Pelicula(
    id INT PRIMARY KEY AUTO_INCREMENT,
    titol VARCHAR(50) NOT NULL,
    director VARCHAR(50) NOT NULL ,
    genere VARCHAR(50) NOT NULL ,
    exemplars INT NOT NULL
);

Create Table material(
    id INT PRIMARY KEY AUTO_INCREMENT,
    tipus ENUM('Llibre', 'Revista', 'Pelicula') NOT NULL,
    id_material INT NOT NULL,
    FOREIGN KEY (id_material) REFERENCES Llibre(id) ON DELETE CASCADE,
    FOREIGN KEY (id_material) REFERENCES Revista(id) ON DELETE CASCADE,
    FOREIGN KEY (id_material) REFERENCES Pelicula(id) ON DELETE CASCADE
);

create table Biblioteca(
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(50) NOT NULL,
    adreca VARCHAR(100) NOT NULL
);

create table Biblio_Soci(
    id_biblioteca INT,
    id_soci INT,
    PRIMARY KEY (id_biblioteca, id_soci),
    FOREIGN KEY (id_biblioteca) REFERENCES Biblioteca(id) ON DELETE CASCADE,
    FOREIGN KEY (id_soci) REFERENCES Socis(id) ON DELETE CASCADE
);

create table Biblio_Admin(
    id_biblioteca INT,
    id_admin INT,
    PRIMARY KEY (id_biblioteca, id_admin),
    FOREIGN KEY (id_biblioteca) REFERENCES Biblioteca(id) ON DELETE CASCADE,
    FOREIGN KEY (id_admin) REFERENCES Adminitrasores(id) ON DELETE CASCADE
);

create table Biblio_Material(
    id_biblioteca INT,
    id_material INT,
    PRIMARY KEY (id_biblioteca, id_material),
    FOREIGN KEY (id_biblioteca) REFERENCES Biblioteca(id) ON DELETE CASCADE,
    FOREIGN KEY (id_material) REFERENCES material(id) ON DELETE CASCADE
);

create table prestecs(
    id_soci INT,
    id_material INT,
    PRIMARY KEY (id_soci, id_material),
    FOREIGN KEY (id_soci) REFERENCES Socis(id) ON DELETE CASCADE,
    FOREIGN KEY (id_material) REFERENCES material(id) ON DELETE CASCADE
);


Insert into Adminitrasores (nombre, DNI, carrec) values
('Ana Garcia', '12345678A', 'Directora'),
('Luis Martinez', '87654321B', 'Bibliotecario'),
('Marta Lopez', '11223344C', 'Asistente');

Insert into Socis (nombre, DNI) values
('Carlos Sanchez', '22334455D'),
('Laura Fernandez', '33445566E'),
('Javier Gomez', '44556677F');

Insert into Llibre (titol, autor, exemplars) values
('Cien años de soledad', 'Gabriel Garcia Marquez', 5),
('Don Quijote de la Mancha', 'Miguel de Cervantes', 3),
('La sombra del viento', 'Carlos Ruiz Zafon', 4);

Insert into Revista (titol, data, exemplars) values
('National Geographic', '2023-01-15', 10),
('Time', '2023-02-20', 8),
('Forbes', '2023-03-10', 6);

Insert into Pelicula (titol, director, genere, exemplars) values
('Inception', 'Christopher Nolan', 'Sci-Fi', 7),
('The Godfather', 'Francis Ford Coppola', 'Crime', 5),
('Pulp Fiction', 'Quentin Tarantino', 'Drama', 4);

Insert into Biblioteca (nom, adreca) values
('Biblioteca Central', 'Calle Mayor 1, Ciudad');

Insert into material (tipus, id_material) values
('Llibre', 1),
('Llibre', 2),
('Llibre', 3),
('Revista', 1),
('Revista', 2),
('Revista', 3),
('Pelicula', 1),
('Pelicula', 2),
('Pelicula', 3);

Insert into Biblio_Soci (id_biblioteca, id_soci) values
(1, 1),
(1, 2),
(1, 3);

Insert into Biblio_Admin (id_biblioteca, id_admin) values
(1, 1),
(1, 2),
(1, 3);

Insert into Biblio_Material (id_biblioteca, id_material) values
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(1, 9);

