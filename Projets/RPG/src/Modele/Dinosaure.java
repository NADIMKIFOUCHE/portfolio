package Modele;

public class Dinosaure extends Ennemi {
    public Dinosaure( int pv, int attaque) {
        super( pv, attaque);
    }

    @Override
    public String toString() {
        return "Dinosaure";
    }

    @Override
    void attaquer(Personnage p) {
        p.setPv(p.getPv()-this.getAttaque());
    }
}
