import { Persona } from "./Persona.js";
import { Libro } from "./Libro.js";
import { Revista } from "./Revista.js";
import { Pelicula } from "./Pelicula.js";
export class Socis extends Persona {
    constructor(nom, DNI) {
        super(nom, DNI);
        this.llistaProductes = {
            libros: [],
            revistas: [],
            peliculas: []
        };
    }

    getLlistaProductes() {
        return this.llistaProductes;
    }

    possarProducte(producte) {
        if (producte instanceof Libro) {
            this.llistaProductes.libros.push(producte);
        } else if (producte instanceof Revista) {
            this.llistaProductes.revistas.push(producte);
        } else if (producte instanceof Pelicula) {
            this.llistaProductes.peliculas.push(producte);
        } else {
            console.log("Tipo de producto no reconocido.");
        }
    }

    getNumProductes() {
        return this.llistaProductes.libros.length + this.llistaProductes.revistas.length + this.llistaProductes.peliculas.length;
    }

}