package Modele;

import java.util.Random;

public class Chasseur extends PersonnagePrehistoire {

    public Chasseur(String nom){
        super(nom);
        this.setPv(100);
        this.setDegats(10);
        this.setForce(6);
        this.setHabilite(15);
    }
    @Override
    public String afficherPerso() {
        return
                "Nom : " + this.getNom() + '\n' +
                        "Type : Chasseur\n"+
                "Inventaire :" + this.getInventaire().toString()+ '\n' +
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
