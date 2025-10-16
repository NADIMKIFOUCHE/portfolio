package Modele;

public class FabriqueObjetPrehistoire implements FabriqueObjetPrehistoireInterface{
    @Override
    public Objet CreerViande( ) {
        return new Viande();
    }

    @Override
    public Objet CreerFruit( ) {
        return new Fruit();
    }
}
