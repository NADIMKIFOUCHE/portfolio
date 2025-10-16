package Modele;

public class Mammouth extends Ennemi {
    public Mammouth( int pv, int attaque) {
        super( pv, attaque);
    }

    @Override
    public String toString() {
        return "Mammouth";
    }

    @Override
    void attaquer(Personnage p) {
        p.setPv(p.getPv()-this.getAttaque());
    }
}
