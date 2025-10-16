package Modele;

import java.util.Random;

public class HommeDesCavernes extends PersonnagePrehistoire {

    public HommeDesCavernes(String nom) {
        super(nom);
        this.setPv(100);
        this.setDegats(18);
        this.setForce(15);
        this.setHabilite(4);
    }

    @Override
    public String afficherPerso() {
        return
                "Nom : " + this.getNom() + '\n' +
                        "Type : Homme des cavernes\n"+
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
