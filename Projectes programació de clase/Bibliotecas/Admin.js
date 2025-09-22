 import { Persona } from "./Persona.js";
 export class Admin extends Persona {
    constructor(nom, DNI, carrec) {
        super(nom, DNI);
        this.carrec = carrec;
    }
    getCarrec() {
        return this.carrec;
    }
    setCarrec(carrec) {
        this.carrec = carrec;
    }
}