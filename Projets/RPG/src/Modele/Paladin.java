package Modele;

import java.util.Random;

public class Paladin extends PersonnageFantastique {
    public Paladin (String nom) {
        super(nom);
        this.setPv(100);
        this.setDegats(15);
        this.setForce(10);
        this.setHabilite(10);
    }

    @Override
    public String afficherPerso() {
        return
                "Nom : " + this.getNom() + '\n' +
                        "Type : Paladin\n"+
                        "Inventaire :" + this.getInventaire()+ '\n' +
                        "Arme : " + this.getArme() +'\n' +
                        "Pv : " + this.getPv() +'\n' +
                        "Force : " + this.getForce() +'\n' +
                        "Habilite : " + this.getHabilite() +'\n' +
                        "Degats : " + this.getDegats() +'\n' +
                        "Bonus de Force : " + this.getBonusForce()+'\n' +
                        "Bonus d'Habilite : " + this.getBonusHabilite() +'\n' +
                        "Bonus de Degats : " + this.getBonusDegats() +'\n';
    }


}
