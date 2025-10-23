package Modele;

public class Combat extends Etat {
    @Override
    public void actionHome(boolean choix) {

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
        return new Home();
    }
}
