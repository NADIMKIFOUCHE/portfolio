package Modele;

public class FabriqueArmePrehistoire implements FabriqueArmePrehistoireInterface {
    @Override
    public Arme creerArmePierre( ) {
        return new Pierre( );
    }

    @Override
    public Arme creerArmeFronde() {
        return new Fronde( );
    }
}
