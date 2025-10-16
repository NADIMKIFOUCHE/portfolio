package Modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PartiePrehistoire extends Partie{

    public PartiePrehistoire(Etat etat, Personnage personnage) {
        super(etat, personnage);
    }

    public Personnage creerPersonnage(String typePerso, String nom) { //do while
        Personnage perso = null;
        switch(typePerso.toLowerCase()) {
            case "h":
                perso = new HommeDesCavernes(nom);
                break;
            case "c":
                perso = new Chasseur(nom);
                break;
        }
        return perso;
    }

    @Override
    public void initialiserInventaire() {
        FabriqueObjetPrehistoire FabriqueObjet= new FabriqueObjetPrehistoire();
        FabriqueArmePrehistoire FabriqueArme= new FabriqueArmePrehistoire();
        Arme a= FabriqueArme.creerArmePierre();
        Objet o= FabriqueObjet.CreerViande();
        this.getPersonnage().getInventaire().ajouterObjet(a);
        this.getPersonnage().getInventaire().ajouterObjet(o);
    }

    @Override
    public Donjon creerDonjonFacile() {
        Donjon d=new Donjon();
        d.setTypeDonjon(1);
        Piece piece = new Piece();
        d.setPieces(creerPiecesFacile());
        return d;
    }

    @Override
    public Donjon creerDonjonMoyen() {
        Donjon d=new Donjon();
        d.setTypeDonjon(2);
        Piece piece = new Piece();
        d.setPieces(creerPiecesMoyen());
        return d;
    }

    @Override
    public Donjon creerDonjonDifficile() {
        Donjon d=new Donjon();
        d.setTypeDonjon(3);
        Piece piece = new Piece();
        d.setPieces(creerPiecesDifficile());
        return d;
    }
    public List<Piece> creerPiecesFacile(){
        Random random=new Random();
        List<Piece>l=new ArrayList<>();
        Piece p = new Piece();
        l.add(p);
        FabriqueEnnemiPrehistoire FabriqueEnnemi=new FabriqueEnnemiPrehistoire();
        FabriqueObjetPrehistoire FabriqueObjet=new FabriqueObjetPrehistoire();
        FabriqueArmePrehistoire FabriqueArme=new FabriqueArmePrehistoire();
        int nbEnnemi= random.nextInt(3)+1;
        int nbObjet= random.nextInt(2)+1;
        int choix;
        int pv;
        int att;
        int id =1;
        for (Piece piece : l) {
            for (int i = 0; i < nbEnnemi + 1; i++) {
                pv = random.nextInt(10)+1 ;
                att = random.nextInt(2) +1;
                choix = random.nextInt(3);
                switch (choix) {
                    case 0:
                        piece.ajouterEnnemi(id, FabriqueEnnemi.creerMammouth(pv, att));
                        id++;
                        break;
                    case 1:
                        piece.ajouterEnnemi(id, FabriqueEnnemi.creerDinosaure(pv, att));
                        id++;
                        break;
                    case 2:
                        piece.ajouterEnnemi(id, FabriqueEnnemi.creerTigreADentsDeSabres(pv, att));
                        id++;
                        break;
                }
            }
            for (int i = 0; i < nbEnnemi + 1; i++) {
                choix = random.nextInt(4);
                switch (choix) {
                    case 0:
                        piece.ajouterObjet(id, FabriqueObjet.CreerViande());
                        id++;
                        break;
                    case 1:
                        piece.ajouterObjet(id, FabriqueObjet.CreerFruit());
                        id++;
                        break;
                    case 2:
                        piece.ajouterObjet(id, FabriqueArme.creerArmeFronde());
                        id++;
                        break;
                    case 3:
                        piece.ajouterObjet(id, FabriqueArme.creerArmePierre());
                        id++;
                        break;
                }
            }
        }
        return l;
    }
    public List<Piece> creerPiecesMoyen(){
        Random random=new Random();
        List<Piece>l=new ArrayList<>();
        FabriqueEnnemiPrehistoire FabriqueEnnemi=new FabriqueEnnemiPrehistoire();
        FabriqueObjetPrehistoire FabriqueObjet=new FabriqueObjetPrehistoire();
        FabriqueArmePrehistoire FabriqueArme=new FabriqueArmePrehistoire();
        Piece p = new Piece();
        Piece p1 = new Piece();
        l.add(p);
        l.add(p1);
        int nbEnnemi= random.nextInt(3)+1;
        int nbObjet= random.nextInt(2)+1;
        int choix;
        int pv;
        int att;
        int id =1;
        for (Piece piece : l) {
            for (int i = 0; i < nbEnnemi + 1; i++) {
                pv = random.nextInt(10,20) ;
                att = random.nextInt(1,4) ;
                choix = random.nextInt(3);
                switch (choix) {
                    case 0:
                        piece.ajouterEnnemi(id, FabriqueEnnemi.creerMammouth(pv, att));
                        id++;
                        break;
                    case 1:
                        piece.ajouterEnnemi(id, FabriqueEnnemi.creerDinosaure(pv, att));
                        id++;
                        break;
                    case 2:
                        piece.ajouterEnnemi(id, FabriqueEnnemi.creerTigreADentsDeSabres(pv, att));
                        id++;
                        break;
                }
            }
            for (int i = 0; i < nbEnnemi + 1; i++) {
                choix = random.nextInt(4);
                switch (choix) {
                    case 0:
                        piece.ajouterObjet(id, FabriqueObjet.CreerViande());
                        id++;
                        break;
                    case 1:
                        piece.ajouterObjet(id, FabriqueObjet.CreerFruit());
                        id++;
                        break;
                    case 2:
                        piece.ajouterObjet(id, FabriqueArme.creerArmeFronde());
                        id++;
                        break;
                    case 3:
                        piece.ajouterObjet(id, FabriqueArme.creerArmePierre());
                        id++;
                        break;
                }
            }
        }
        return l;
    }
    public List<Piece> creerPiecesDifficile(){
        Random random=new Random();
        List<Piece>l=new ArrayList<>();
        FabriqueEnnemiPrehistoire FabriqueEnnemi=new FabriqueEnnemiPrehistoire();
        FabriqueObjetPrehistoire FabriqueObjet=new FabriqueObjetPrehistoire();
        FabriqueArmePrehistoire FabriqueArme=new FabriqueArmePrehistoire();
        Piece p = new Piece();
        Piece p1 = new Piece();
        Piece p2=new Piece();
        l.add(p);
        l.add(p1);
        l.add(p2);
        int nbEnnemi= random.nextInt(3)+2;
        int nbObjet= random.nextInt(2)+1;
        int choix;
        int pv;
        int att;
        int id =1;
        for (Piece piece : l) {
            for (int i = 0; i < nbEnnemi + 1; i++) {
                pv = random.nextInt(20,25) ;
                att = random.nextInt(3,6) ;
                choix = random.nextInt(3);
                switch (choix) {
                    case 0:
                        piece.ajouterEnnemi(id, FabriqueEnnemi.creerMammouth(pv, att));
                        id++;
                        break;
                    case 1:
                        piece.ajouterEnnemi(id, FabriqueEnnemi.creerDinosaure(pv, att));
                        id++;
                        break;
                    case 2:
                        piece.ajouterEnnemi(id, FabriqueEnnemi.creerTigreADentsDeSabres(pv, att));
                        id++;
                        break;
                }
            }
            for (int i = 0; i < nbEnnemi + 1; i++) {
                choix = random.nextInt(4);
                switch (choix) {
                    case 0:
                        piece.ajouterObjet(id, FabriqueObjet.CreerViande());
                        id++;
                        break;
                    case 1:
                        piece.ajouterObjet(id, FabriqueObjet.CreerFruit());
                        id++;
                        break;
                    case 2:
                        piece.ajouterObjet(id, FabriqueArme.creerArmeFronde());
                        id++;
                        break;
                    case 3:
                        piece.ajouterObjet(id, FabriqueArme.creerArmePierre());
                        id++;
                        break;
                }
            }
        }
        return l;
    }

    @Override
    public Donjon recreerDonjon(Donjon d) {
        if(d.getTypeDonjon()==1){
            return creerDonjonFacile();
        } else if (d.getTypeDonjon()==2) {
            return creerDonjonMoyen();
        }
        else{
            creerDonjonDifficile();
        }
        return null;
    }
}
