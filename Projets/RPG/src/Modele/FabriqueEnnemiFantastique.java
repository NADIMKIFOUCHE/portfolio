package Modele;

public class FabriqueEnnemiFantastique implements FabriqueEnnemiFantastiqueInterface{
    @Override
    public Ennemi creerOgre(  int pv,int attaque) {
        return new Ogre( pv, attaque);
    }

    @Override
    public Ennemi creerGobelin( int pv,int attaque) {
        return new Gobelin( pv, attaque);
    }

    @Override
    public Ennemi creerSorcierMalefique( int pv,int attaque) {
        return new SorcierMalefique(pv, attaque);
    }
}
