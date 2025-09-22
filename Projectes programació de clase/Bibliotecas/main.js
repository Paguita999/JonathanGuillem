import { Libro } from "./Libro.js";
import { Revista } from "./Revista.js";
import { Socis } from "./Socis.js";
import { Admin } from "./Admin.js";
import { Pelicula } from "./Pelicula.js";
import { Biblioteca } from "./Biblioteca.js";
import readlineSync from 'readline-sync';

function agregarLlibre(){
    let tituloLibro = readlineSync.question("Título del libro: ");
    let unidades_libro;
    do {
        unidades_libro = readlineSync.question("Unidades del libro (número entero): ");
    } while (!Number.isInteger(Number(unidades_libro)) || Number(unidades_libro) < 1);
    unidades_libro = Number(unidades_libro);
    let autorLibro = readlineSync.question("Autor del libro: ");
    let libro = new Libro(tituloLibro, unidades_libro, autorLibro);
    productos.libros.push(libro);
    console.log("Libro agregado:", libro);
}

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
            console.log("6. Prestar producto a soci");
            console.log("7. Devolver producto de soci");
            console.log("8. Mostrar todos los productos");
            console.log("9. Mostrar todos los socios");
            console.log("10. Mostrar todos los administradores de préstamos");
            console.log("11. Mostrar recursos prestados por cada soci");
            console.log("0. Salir");
            opción = parseInt(readlineSync.question("Seleccione una opción: "));
            switch (opción) {
                case 1:
                    
                    break;
                case 2:
                    agregarLlibre

                    break;
                case 3:
                    let tituloPelicula = readlineSync.question("Título de la película: ");
                    let unidades_pelicula;
                    do {
                        unidades_pelicula = readlineSync.question("Unidades de la película (número entero): ");
                    } while (!Number.isInteger(Number(unidades_pelicula)) || Number(unidades_pelicula) < 1);
                    unidades_pelicula = Number(unidades_pelicula);
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
                    let dniSoci = readlineSync.question("DNI del soci: ");
                    let sociaPrestar = socis.find(s => s.getDNI() === dniSoci);
                    if (!sociaPrestar) {
                        console.log("Soci no trobat.");
                        break;
                    }
                    if (sociaPrestar.getNumProductes() >= 3) {
                        console.log("El soci ja té el màxim de préstecs (3).");
                        break;
                    }
                    // Elegir tipo de producto
                    let tipo = readlineSync.question("Tipo de producto (libro/revista/pelicula): ").toLowerCase();
                    let lista;
                    if (tipo === "libro") {
                        lista = productos.libros;
                    } else if (tipo === "revista") {
                        lista = Object.values(productos.revistas).flat();
                    } else if (tipo === "pelicula") {
                        lista = Object.values(productos.peliculas).flat();
                    } else {
                        console.log("Tipo de producto no válido.");
                        break;
                    }

                    let titulo = readlineSync.question("Título del producto: ");
                    let producto = lista.find(p => p.getTitol() === titulo && p.getExemplars() > 0);
                    if (!producto) {
                        console.log("Producto no disponible.");
                        break;
                    }

                    producto.setExemplars(producto.getExemplars() - 1);
                    sociaPrestar.possarProducte(producto);
                    console.log(`Producto prestado a ${sociaPrestar.getNom()}:`, producto);
                    break;
                case 7:
                    let dniSociDevolver = readlineSync.question("DNI del soci: ");
                    let sociDevolver = socis.find(s => s.getDNI() === dniSociDevolver);
                    if (!sociDevolver) {
                        console.log("Soci no trobat.");
                        break;
                    }
                    let tituloDevolver = readlineSync.question("Título del producto a devolver: ");
                    let productoDevolver = null;
                    for (let tipo in sociDevolver.getLlistaProductes()) {
                        productoDevolver = sociDevolver.getLlistaProductes()[tipo].find(p => p.getTitol() === tituloDevolver);
                        if (productoDevolver) {
                            sociDevolver.getLlistaProductes()[tipo] = sociDevolver.getLlistaProductes()[tipo].filter(p => p.getTitol() !== tituloDevolver);
                            break;
                        }
                    }
                    if (!productoDevolver) {
                        console.log("El soci no té aquest producte.");
                        break;
                    }
                    productoDevolver.setExemplars(productoDevolver.getExemplars() + 1);
                    console.log(`Producto retornado por ${sociDevolver.getNom()}:`, productoDevolver);
                    break;
                case 8:
                    let tipoFiltro = readlineSync.question("Filtrar por tipo (libro/revista/pelicula/tots): ").toLowerCase();

                    if (tipoFiltro === "libro" || tipoFiltro === "tots") {
                        console.log("Llibres:");
                        productos.libros.forEach(libro => {
                            console.log(libro);
                        });
                        break;
                    }

                    if (tipoFiltro === "revista" || tipoFiltro === "tots") {
                        let anyFiltro = readlineSync.question("Vols filtrar per any de publicació? (Introdueix l'any o deixa buit): ");
                        console.log("Revistes:");
                        Object.entries(productos.revistas).forEach(([any, revistes]) => {
                            if (!anyFiltro || any === anyFiltro) {
                                revistes.forEach(revista => {
                                    console.log(revista);
                                });
                            }
                        });
                        break;
                    }

                    if (tipoFiltro === "pelicula" || tipoFiltro === "tots") {
                        let genereFiltro = readlineSync.question("Vols filtrar per gènere? (Introdueix el gènere o deixa buit): ");
                        console.log("Pel·lícules:");
                        Object.entries(productos.peliculas).forEach(([genere, pelicules]) => {
                            if (!genereFiltro || genere === genereFiltro) {
                                pelicules.forEach(pelicula => {
                                    console.log(pelicula);
                                });
                            }
                        });
                        break;
                    }
                case 9:
                    console.log("Llista de socis:");
                    socis.forEach(soci => {
                        console.log(soci);
                    });
                    break;
                case 10:
                    console.log("Llista d'administradors de préstecs:");
                    admins.forEach(admin => {
                        console.log(admin);
                    });
                    break;
                case 11:
                    console.log("Recursos prestats per cada soci:");
                    socis.forEach(soci => {
                        console.log(`Soci: ${soci.getNom()} (${soci.getDNI()})`);
                        const llistaProductes = soci.getLlistaProductes();
                        let tienePrestamos = false;
                        for (let tipo in llistaProductes) {
                            if (llistaProductes[tipo].length > 0) {
                                tienePrestamos = true;
                                llistaProductes[tipo].forEach(producto => {
                                    console.log(`  - ${tipo}: ${producto.getTitol()}`);
                                });
                            }
                        }
                        if (!tienePrestamos) {
                            console.log("  No té recursos prestats.");
                        }
                    });
                    break;
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