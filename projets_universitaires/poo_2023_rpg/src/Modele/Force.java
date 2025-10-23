package Modele;

public abstract class Force extends Consommable{
    private int forceAjoute;


    public Force() {}

    public int getForceAjoute() {
        return forceAjoute;
    }

    public void setForceAjoute(int forceAjoute) {
        this.forceAjoute = forceAjoute;
    }

    public void utiliser(Personnage p){
        p.setBonusForce(p.getBonusForce()+forceAjoute);
    }
    public abstract String toString() ;


}
