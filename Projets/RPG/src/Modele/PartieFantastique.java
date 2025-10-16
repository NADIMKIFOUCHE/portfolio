package Modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PartieFantastique extends Partie{
    public PartieFantastique(Etat etat, Personnage personnage) {
        super(etat, personnage);
    }

    public Personnage creerPersonnage(String typePerso, String nom) {
        Personnage perso = null;
        switch(typePerso.toLowerCase()) {
            case "m":
                perso = new Mage(nom);
                break;
            case "p":
                perso = new Paladin(nom);
                break;
        }
        return perso;
    }

    @Override
    public void initialiserInventaire() {
        FabriqueObjetFantastique FabriqueObjet= new FabriqueObjetFantastique();
        FabriqueArmeFantastique FabriqueArme= new FabriqueArmeFantastique();
        Arme a= FabriqueArme.creerArmeEpeeMagique();
        Objet o= FabriqueObjet.CreerPotionDeVie();
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
        List<Piece>l=new ArrayList<>();
        Piece p = new Piece();
        Random random=new Random();
        FabriqueEnnemiFantastique FabriqueEnnemi=new FabriqueEnnemiFantastique();
        FabriqueObjetFantastique FabriqueObjet=new FabriqueObjetFantastique();
        FabriqueArmeFantastique FabriqueArme=new FabriqueArmeFantastique();
        int nbEnnemi= random.nextInt(3)+1;
        int nbObjet= random.nextInt(2)+1;
        int choix;
        int pv;
        int att;
        int id =1;
        for(int i=0;i<nbEnnemi+1;i++){
            pv= random.nextInt(10)+1;
            att=random.nextInt(2)+1;
            choix= random.nextInt(3);
            switch (choix){
                case 0:
                    p.ajouterEnnemi(id,FabriqueEnnemi.creerGobelin(pv,att));
                    id++;
                    break;
                case 1:
                    p.ajouterEnnemi(id, FabriqueEnnemi.creerOgre(pv, att));
                    id++;
                    break;
                case 2:
                    p.ajouterEnnemi(id,FabriqueEnnemi.creerSorcierMalefique(pv,att));
                    id++;
                    break;
            }
        }
        for(int i=0;i<nbEnnemi+1;i++){
            choix= random.nextInt(4);
            switch (choix){
                case 0:
                    p.ajouterObjet(id,FabriqueObjet.CreerPotionDeVie());
                    id++;
                    break;
                case 1:
                    p.ajouterObjet(id,FabriqueObjet.CreerPotionDeForce());
                    id++;
                    break;
                case 2:
                    p.ajouterObjet(id,FabriqueArme.creerArmeEpeeMagique());
                    id++;
                    break;
                case 3:
                    p.ajouterObjet(id,FabriqueArme.creerArmeLivreDeMagie());
                    id++;
                    break;
            }
        }
        l.add(p);
        return l;
    }
    public List<Piece> creerPiecesMoyen(){
        Piece p = new Piece();
        Piece p1 = new Piece();
        List<Piece>l=new ArrayList<>();
        l.add(p);
        l.add(p1);
        Random random=new Random();
        FabriqueEnnemiFantastique FabriqueEnnemi=new FabriqueEnnemiFantastique();
        FabriqueObjetFantastique FabriqueObjet=new FabriqueObjetFantastique();
        FabriqueArmeFantastique FabriqueArme=new FabriqueArmeFantastique();
        int nbEnnemi= random.nextInt(3)+2;
        int nbObjet= random.nextInt(2)+1;
        int choix;
        int pv;
        int att;
        int id =1;
        for (Piece piece : l) {
            for (int i = 0; i < nbEnnemi + 1; i++) {
                pv = random.nextInt(10,20) ;
                att = random.nextInt(1,3) ;
                choix = random.nextInt(3);
                switch (choix) {
                    case 0:
                        piece.ajouterEnnemi(id, FabriqueEnnemi.creerGobelin(pv, att));
                        id++;
                        break;
                    case 1:
                        piece.ajouterEnnemi(id, FabriqueEnnemi.creerOgre(pv, att));
                        id++;
                        break;
                    case 2:
                        piece.ajouterEnnemi(id, FabriqueEnnemi.creerSorcierMalefique(pv, att));
                        id++;
                        break;
                }
            }
            for (int i = 0; i < nbEnnemi + 1; i++) {
                choix = random.nextInt(4);
                switch (choix) {
                    case 0:
                        piece.ajouterObjet(id, FabriqueObjet.CreerPotionDeVie());
                        id++;
                        break;
                    case 1:
                        piece.ajouterObjet(id, FabriqueObjet.CreerPotionDeForce());
                        id++;
                        break;
                    case 2:
                        piece.ajouterObjet(id, FabriqueArme.creerArmeEpeeMagique());
                        id++;
                        break;
                    case 3:
                        piece.ajouterObjet(id, FabriqueArme.creerArmeLivreDeMagie());
                        id++;
                        break;
                }
            }
        }
        return l;
    }
    public List<Piece> creerPiecesDifficile(){
        Random random=new Random();
        FabriqueEnnemiFantastique FabriqueEnnemi=new FabriqueEnnemiFantastique();
        FabriqueObjetFantastique FabriqueObjet=new FabriqueObjetFantastique();
        FabriqueArmeFantastique FabriqueArme=new FabriqueArmeFantastique();
        List<Piece>l=new ArrayList<>();
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
        int nbPiece= random.nextInt(3,6);
        for (Piece piece : l) {
            for (int i = 0; i < nbEnnemi + 1; i++) {
                pv = random.nextInt(20,25) ;
                att = random.nextInt(3,5) ;
                choix = random.nextInt(3);
                switch (choix) {
                    case 0:
                        piece.ajouterEnnemi(id, FabriqueEnnemi.creerGobelin(pv, att));
                        id++;
                        break;
                    case 1:
                        piece.ajouterEnnemi(id, FabriqueEnnemi.creerOgre(pv, att));
                        id++;
                        break;
                    case 2:
                        piece.ajouterEnnemi(id, FabriqueEnnemi.creerSorcierMalefique(pv, att));
                        id++;
                        break;
                }
            }
            for (int i = 0; i < nbEnnemi + 1; i++) {
                choix = random.nextInt(4);
                switch (choix) {
                    case 0:
                        piece.ajouterObjet(id, FabriqueObjet.CreerPotionDeVie());
                        id++;
                        break;
                    case 1:
                        piece.ajouterObjet(id, FabriqueObjet.CreerPotionDeForce());
                        id++;
                        break;
                    case 2:
                        piece.ajouterObjet(id, FabriqueArme.creerArmeEpeeMagique());
                        id++;
                        break;
                    case 3:
                        piece.ajouterObjet(id, FabriqueArme.creerArmeLivreDeMagie());
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
