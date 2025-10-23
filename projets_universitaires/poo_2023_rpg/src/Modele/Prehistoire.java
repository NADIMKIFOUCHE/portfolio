package Modele;

public class Prehistoire extends Theme{
    public Prehistoire() {
    }

    @Override
    public Partie creerPartie(Etat etat, Personnage perso) {
        Partie partie = new PartiePrehistoire(etat, perso);
        return partie;
    }
}

