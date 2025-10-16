package Modele;

public class Arme extends Objet {
    private int degat;

    public int getDegat() {
        return degat;
    }

    public void setDegat(int degat) {
        this.degat = degat;
    }

    public Arme() {
    }

    @Override
    public String toString() {
        return "Arme";
    }
    @Override
    public void utiliser(Personnage p) {
        if (p.getArme()==null){
            p.setArme(this);
        }
        else{
            if (p.getInventaire().estComplet()==false){
                p.getInventaire().ajouterObjet(p.getArme());
                p.setArme(this);
            }
            else{
                p.setArme(this);
            }
        }
    }

    @Override
    public boolean estConsommable() {
        return false;
    }
}
