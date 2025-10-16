package Modele;

public class Gobelin extends Ennemi{
    public Gobelin(int pv, int attaque) {
        super(pv, attaque);
    }

    @Override
    public String toString() {
        return "Gobelin";
    }

    @Override
    void attaquer(Personnage p) {
        p.setPv(p.getPv()-this.getAttaque());
    }
}
