import { Libro } from "./Libro.js";
import { Revista } from "./Revista.js";
import { Socis } from "./Socis.js";
import { Admin } from "./Admin.js";
import { Pelicula } from "./Pelicula.js";
import readlineSync from 'readline-sync';

export class Biblioteca {
    constructor() {
        this.llibres = [];
        this.revistes = [];
        this.peliculas = [];
        this.socis = [];
        this.admins = [];
    }
    agregarLlibre(llibre) {
        this.llibres.push(llibre);
    }
    agregarRevista(revista) {
        if (!this.revistes[revista.getFechaPublicacion()]) {
            this.revistes[revista.getFechaPublicacion] = [];
        }
        this.revistes[revista.getFechaPublicacion].push(pelicula);
    }

    agregarPelicula(pelicula) {
        if (!this.peliculas[pelicula.generoPelicula]) {
            this.peliculas[pelicula.generoPelicula] = [];
        }
        this.peliculas[pelicula.generoPelicula].push(pelicula);
    }
}