package Modele;

public class SorcierMalefique extends Ennemi{
    public SorcierMalefique(int pv, int attaque) {
        super(pv, attaque);
    }

    @Override
    public String toString() {
        return "SorcierMalefique";
    }

    @Override
    void attaquer(Personnage p) {
        p.setPv(p.getPv()-this.getAttaque());
    }
}
