export class Producto {
    constructor(titol,exemplars) {
        this.titol = titol;
        this.exemplars = exemplars;
    }
    getTitol() {
        return this.titol;
    }
    getExemplars() {
        return this.exemplars;
    }
    setExemplars(exemplars) {
        this.exemplars = exemplars;
    }   
    setTitol(titol) {
        this.titol = titol;
    }
}