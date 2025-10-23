package Modele;

import java.util.Random;

public abstract class Personnage { //N
    private String nom;

    private Inventaire inventaire;

    private Arme arme;
    private int pv;
    private int force;
    private int habilite;
    private int degats;
    private int bonusForce;
    private int bonusHabilite;
    private int bonusDegats;

    private int nbRespawn = 2;

    private EtatPersonnage etatPrecedent;

    public Personnage(String nom) {
        this.nom = nom;
        this.inventaire = new Inventaire();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Inventaire getInventaire() {
        return inventaire;
    }

    public void setInventaire(Inventaire inventaire) {
        this.inventaire = inventaire;
    }

    public Arme getArme() {
        return arme;
    }

    public void setArme(Arme arme) {
        this.arme = arme;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public int getForce() {
        return force;
    }

    public void setForce(int force) {
        this.force = force;
    }

    public int getHabilite() {
        return habilite;
    }

    public void setHabilite(int habilite) {
        this.habilite = habilite;
    }

    public int getDegats() {
        return degats;
    }

    public void setDegats(int degats) {
        this.degats = degats;
    }

    public int getBonusForce() {
        return bonusForce;
    }

    public void setBonusForce(int bonusForce) {
        this.bonusForce = bonusForce;
    }

    public int getBonusHabilite() {
        return bonusHabilite;
    }

    public void setBonusHabilite(int bonusHabilite) {
        this.bonusHabilite = bonusHabilite;
    }

    public int getBonusDegats() {
        return bonusDegats;
    }

    public void setBonusDegats(int bonusDegats) {
        this.bonusDegats = bonusDegats;
    }

    public int getNbRespawn() {
        return nbRespawn;
    }

    public void setNbRespawn(int nbRespawn) {
        this.nbRespawn = nbRespawn;
    }

    public EtatPersonnage getEtatPrecedent() {
        return etatPrecedent;
    }

    public void setEtatPrecedent(EtatPersonnage etatPrecedent) {
        this.etatPrecedent = etatPrecedent;
    }

    public void updateRespawn() {
        this.setNbRespawn(this.getNbRespawn() - 1);
    }

    public void ramasserObjet(Objet o) {
        inventaire.ajouterObjet(o);
    }

    public abstract String afficherPerso();



    public void attaquer(Ennemi ennemi) {
        Random random = new Random();
        int habilite = random.nextInt(this.getHabilite())+this.getBonusHabilite();
        int force=this.getForce()+this.getBonusForce();
        int degatsfinaux;
        if (this.getArme() == null) {
            degatsfinaux = this.getDegats()+force+habilite;
        }
        else {
            degatsfinaux =this.getDegats()+this.getArme().getDegat()+force+habilite ;
        }
        ennemi.setPv((ennemi.getPv())-degatsfinaux);
    }
    public int degatsPerso(int d){
        return d;
    }

    public void reinitialiser() {
        this.setPv(1);
    }

    public void restaurerEtatPersonnage() {
        this.setPv(etatPrecedent.getPv());
        this.setInventaire(etatPrecedent.getInventaire());
        this.setEtatPrecedent(null);
    }

}
