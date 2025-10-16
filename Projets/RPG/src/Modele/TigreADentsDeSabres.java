package Modele;

public class TigreADentsDeSabres extends Ennemi {
    public TigreADentsDeSabres( int pv, int attaque) {
        super(pv, attaque);
    }

    @Override
    public String toString() {
        return "TigreADentsDeSabres";
    }

    @Override
    void attaquer(Personnage p) {
        p.setPv(p.getPv()-this.getAttaque());
    }
}
