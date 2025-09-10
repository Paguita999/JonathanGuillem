import { Producto } from "./Producto.js";
 export class Revista extends Producto {
    constructor(titol, exemplars, data) {
        super(titol, exemplars);
        this.data = data;
    }
    getData() {
        return this.data;
    }
    setData(data) {
        this.data = data;
    }
}