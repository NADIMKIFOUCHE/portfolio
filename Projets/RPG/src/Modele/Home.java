package Modele;

public class Home extends Etat {

    @Override
    public void actionHome(boolean choix) {
        if(choix){
            Etat etat = new Combat();
            this.getPartie().setEtat(etat);
        }
    }

    @Override
    public void entrerDonjon() {

    }

    @Override
    public void lancerCombat() {

    }

    @Override
    public void finCombat() {

    }

    @Override
    public void pieceSuivante() {

    }

    @Override
    public Etat perdu() {
        return null;
    }

}