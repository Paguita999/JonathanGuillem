import { Libro } from "./Libro.js";
import { Revista } from "./Revista.js";
import { Socis } from "./Socis.js";
import { Admin } from "./Admin.js";
import { Producto } from "./Producto.js";
import { Persona } from "./Persona.js";
import { Pelicula } from "./Pelicula.js";
import readlineSync from 'readline-sync';



function inicio(){
    let socis = [];
    let admins = [];
    let productos = {
        libros: [],
        revistas: [],
        peliculas: []
    };
    try {
        let opción = -1;
        while (opción != 0) {
            console.log("1. Agregar libro");
            console.log("2. Agregar revista");
            console.log("3. Agregar película");
            console.log("4. Agregar soci");
            console.log("5. Agregar admin");
            console.log("0. Salir");
            opción = parseInt(readlineSync.question("Seleccione una opción: "));
            switch (opción) {
                case 1:
                    let tituloLibro = readlineSync.question("Título del libro: ");
                    let unidades_libro = readlineSync.question("Unidades del libro: ");
                    let autorLibro = readlineSync.question("Autor del libro: ");
                    let libro = new Libro(tituloLibro, unidades_libro, autorLibro);
                    productos.libros.push(libro);
                    console.log("Libro agregado:", libro);
                    break;
                case 2:
                    let tituloRevista = readlineSync.question("Título de la revista: ");
                    let unidades_revista = readlineSync.question("Unidades de la revista: ");
                    let fecha = readlineSync.question("Fecha de la revista: ");
                    let revista = new Revista(tituloRevista, unidades_revista, fecha);
                    if (!productos.revistas[fecha]) {
                        productos.revistas[fecha] = [];
                    }
                    productos.revistas[fecha].push(pelicula);
                    console.log("Revista agregada:", revista);

                    break;
                case 3:
                    let tituloPelicula = readlineSync.question("Título de la película: ");
                    let unidades_pelicula = readlineSync.question("Unidades de la película: ");
                    let directorPelicula = readlineSync.question("Director de la película: ");
                    let generoPelicula = readlineSync.question("Género de la película: ");
                    let pelicula = new Pelicula(tituloPelicula, unidades_pelicula, directorPelicula, generoPelicula);
                    if (!productos.peliculas[generoPelicula]) {
                        productos.peliculas[generoPelicula] = [];
                    }
                    productos.peliculas[generoPelicula].push(pelicula);
                    console.log("Película agregada:", pelicula);
                    break;
                case 4:
                    let nombreSoci = readlineSync.question("Nom del soci: ");
                    let DNI = readlineSync.question("DNI del soci: ");
                    let soci = new Socis(nombreSoci, DNI);
                    socis.push(soci);
                    console.log("Soci afegit:", soci);
                    break;
                case 5:
                    let nomAdmin = readlineSync.question("Nom de l'admin: ");
                    let DNIAdmin = readlineSync.question("DNI de l'admin: ");
                    let carrecAdmin = readlineSync.question("Carrec de l'admin: ");
                    let admin = new Admin(nomAdmin, DNIAdmin, carrecAdmin);
                    admins.push(admin);
                    console.log("Admin afegit:", admin);
                    break;
                case 6:
                    let nomPersona = readlineSync.question("Nom del soci: ");
                case 0:
                console.log("Saliendo...");
                break;
                default:
                console.log("Opción no válida");
                break;
        }
        }
        } catch (error) {
            console.error("Error:", error);
        }

}

inicio();