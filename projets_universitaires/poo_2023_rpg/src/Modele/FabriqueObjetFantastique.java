package Modele;

public class FabriqueObjetFantastique implements FabriqueObjetFantastiqueInterface{
    @Override
    public Objet CreerPotionDeVie() {
        return new PotionDeVie( );
    }

    @Override
    public Objet CreerPotionDeForce( ) {
        return new PotionDeForce( );
    }
}
