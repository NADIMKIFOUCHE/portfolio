package Modele;

public abstract class Etat {
    private Partie partie;


    public Partie getPartie() {
        return partie;
    }

    public void setPartie(Partie partie) {
        this.partie = partie;
    }

    public abstract void actionHome(boolean choix);
    public abstract void entrerDonjon();
    public abstract void lancerCombat();
    public abstract void finCombat();
    public abstract void pieceSuivante();

    public abstract Etat perdu();

}

