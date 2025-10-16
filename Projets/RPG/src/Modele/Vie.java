package Modele;

public abstract class Vie extends Consommable{
    private int vieAjoute;


    public Vie() {}

    public int getVieAjoute() {
        return vieAjoute;
    }

    public void setVieAjoute(int vieAjoute) {
        this.vieAjoute = vieAjoute;
    }

    public void utiliser(Personnage p){
        if(p.getPv()+ vieAjoute>100) {
        p.setPv(100);
        }else{
            p.setPv(p.getPv()+ vieAjoute);
        }
    }

    public abstract String toString() ;
}