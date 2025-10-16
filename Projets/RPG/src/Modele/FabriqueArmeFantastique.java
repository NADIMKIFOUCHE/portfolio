package Modele;

public class FabriqueArmeFantastique implements FabriqueArmeFantastiqueInterface{
    @Override
    public Arme creerArmeEpeeMagique() {
        return new EpeeMagique();
    }

    @Override
    public Arme creerArmeLivreDeMagie() {
        return new LivreDeMagie(  );
    }
}
