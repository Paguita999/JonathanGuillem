import { Producto } from "./Producto.js";

export class Libro extends Producto {
    constructor(titol, exemplars, autor) {
        super(titol, exemplars);
        this.autor = autor;
       
    }
    getAutor() {
        return this.autor;
    }
    setAutor(autor) {
        this.autor = autor;
    }
}