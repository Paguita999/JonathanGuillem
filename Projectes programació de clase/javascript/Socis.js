import { Persona } from "./Persona.js";
export class Socis extends Persona {
    constructor(nom, DNI, llistaexemplars) {
        super(nom, DNI);
        this.llistaexemplars = llistaexemplars;
    }
    getLlistaExemplars() {
        return this.llistaexemplars;
    }
    setLlistaExemplars(llistaexemplars) {
        this.llistaexemplars = llistaexemplars;
    }
}