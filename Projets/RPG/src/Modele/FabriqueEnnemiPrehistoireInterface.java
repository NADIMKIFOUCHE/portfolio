package Modele;

public interface FabriqueEnnemiPrehistoireInterface {
    Ennemi creerMammouth( int pv,int attaque);
    Ennemi creerDinosaure( int pv,int attaque);
    Ennemi creerTigreADentsDeSabres( int pv,int attaque);
}
