package Modele;

public class Pierre extends Arme {
    public Pierre() {
        this.setDegat(10);
    }

    @Override
    public String toString() {
        return "Pierre";
    }
}
