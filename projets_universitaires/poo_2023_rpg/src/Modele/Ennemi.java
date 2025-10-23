package Modele;

public abstract class Ennemi {
    private int pv;

    private int attaque;

    public Ennemi(  int pv, int attaque) {

        this.pv = pv;
        this.attaque = attaque;
    }


    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }


    public int getAttaque() {
        return attaque;
    }

    public void setAttaque(int attaque) {
        this.attaque = attaque;
    }

    abstract void attaquer(Personnage p);
}
