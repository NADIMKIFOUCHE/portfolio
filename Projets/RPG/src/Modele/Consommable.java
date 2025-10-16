package Modele;



public abstract class Consommable extends Objet {
    public Consommable() {
    }
    @Override
    public abstract String toString();
    @Override
    public abstract void utiliser(Personnage p);
    @Override
    public boolean estConsommable() {
        return true;
    }

}
