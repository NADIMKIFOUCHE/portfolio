package Modele;

import java.util.ArrayList;
import java.util.List;

public abstract class Partie {

    private Etat etat;
    private Donjon donjonCourant;
    private List<Donjon> listeDonjon;
    private Personnage personnage;

    public Partie(Etat etat, Personnage personnage) {
        this.etat = etat;
        this.personnage = personnage;
        listeDonjon=new ArrayList<>();
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public Personnage getPersonnage() {
        return personnage;
    }

    public void setPersonnage(Personnage personnage) {
        this.personnage = personnage;
    }

    public Donjon getDonjonCourant() {
        return donjonCourant;
    }

    public void setDonjonCourant(Donjon donjonCourant) {
        this.donjonCourant = donjonCourant;
    }

    public List<Donjon> getListeDonjon() {
        return listeDonjon;
    }

    public void setListeDonjon(List<Donjon> listeDonjon) {
        this.listeDonjon = listeDonjon;
    }
    public void ajouterDonjon(Donjon D){
        listeDonjon.add(D);
    }

    public abstract Personnage creerPersonnage(String typePerso, String nom);

    public abstract Donjon creerDonjonFacile();
    public abstract Donjon creerDonjonMoyen();
    public abstract Donjon creerDonjonDifficile();

    public void retourEnArriere(int i) {
        if (i==1){
            listeDonjon.set(0,creerDonjonFacile());
        }
        else if (i==2){
            listeDonjon.set(0,creerDonjonMoyen());
        }
        else
            listeDonjon.set(0,creerDonjonDifficile());

    }
    public boolean estTerminee(){
        if (listeDonjon.isEmpty()){
            return true ;
        }
        return false;
    }
    public abstract Donjon recreerDonjon(Donjon d);

    public abstract void initialiserInventaire();
}
