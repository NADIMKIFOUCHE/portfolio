package Modele;

public class Fantastique extends Theme {

    @Override
    public Partie creerPartie(Etat etat, Personnage perso) {
        Partie partie = new PartieFantastique(etat, perso);
        return partie;
    }

}

