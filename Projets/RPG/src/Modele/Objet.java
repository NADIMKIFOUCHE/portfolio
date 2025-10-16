package Modele;

public abstract class Objet {


    public Objet() {
    }

    public abstract String toString() ;

    public abstract void utiliser(Personnage p);
    public abstract boolean estConsommable();
}
