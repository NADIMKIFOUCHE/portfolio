package Modele;

public class Ogre extends Ennemi{
    public Ogre( int pv, int attaque) {
        super( pv, attaque);
    }

    @Override
    public String toString() {
        return "Ogre";
    }

    @Override
    void attaquer(Personnage p) {
        p.setPv(p.getPv()-this.getAttaque());
    }
}
