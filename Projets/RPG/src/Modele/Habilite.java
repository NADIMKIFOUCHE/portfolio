package Modele;



public abstract class Habilite extends Consommable {
    private int habiliteAjoute;


    public Habilite() {}

    public int getHabiliteAjoute() {
        return habiliteAjoute;
    }

    public void setHabiliteAjoute(int habiliteAjoute) {
        this.habiliteAjoute = habiliteAjoute;
    }

    public void utiliser(Personnage p){
        p.setBonusHabilite(p.getBonusHabilite()+ habiliteAjoute);
    }

    public abstract String toString() ;
}