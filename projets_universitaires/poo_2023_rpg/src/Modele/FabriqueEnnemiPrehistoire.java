package Modele;

public class FabriqueEnnemiPrehistoire implements FabriqueEnnemiPrehistoireInterface {
    @Override
    public Ennemi creerMammouth( int pv, int attaque) {
        return new Mammouth(pv, attaque);

    }

    @Override
    public Ennemi creerDinosaure( int pv,int attaque) {
        return new Dinosaure( pv, attaque);

    }

    @Override
    public Ennemi creerTigreADentsDeSabres(int pv,int attaque) {
        return new TigreADentsDeSabres(pv,attaque);
    }

}
