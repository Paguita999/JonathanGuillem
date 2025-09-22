import { Producto } from "./Producto.js";

export class Pelicula extends Producto{
    constructor(titol, exemplars, director, gènere) {
        super(titol, exemplars);
        this.director = director;
        this.gènere = gènere;
    }
    getDirector() {
        return this.director;
    }
    getGènere() {
        return this.gènere;
    }
    setDirector(director) {
        this.director = director;
    }
    setGènere(gènere) {
        this.gènere = gènere;
    }
}