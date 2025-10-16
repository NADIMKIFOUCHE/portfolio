package Modele;

public class EtatPersonnage {
    private int pv;
    private Inventaire inventaire;

    public EtatPersonnage(Personnage p) {
        this.pv=p.getPv();
        this.inventaire=p.getInventaire();
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public Inventaire getInventaire() {
        return inventaire;
    }

    public void setInventaire(Inventaire inventaire) {
        this.inventaire = inventaire;
    }
}
