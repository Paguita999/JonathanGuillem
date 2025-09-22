export class Persona {
    constructor(nom,DNI) {
        this.nom = nom;
        this.DNI = DNI;
    }
    getNom() {
        return this.nom;
    }
    getDNI() {
        return this.DNI;
    }
    setNom(nom) {
        this.nom = nom;
    }
    setDNI(DNI) {
        this.DNI = DNI;
    }
}