package Controleur;

import Vue.Ihm;
import Modele.*;
import java.util.Map;
public class Controleur {
    public void jouer() {
        Ihm ihm = new Ihm(null); //creation IHM par default
        String themestr = ihm.choixTheme(); //choix du theme par stt
        StrategieTheme strategieTheme = new StrategieTheme(); //objetstartegie
        Theme theme =  strategieTheme.creerTheme(themestr); // creation du theme
        Etat etat = new Home();
        Partie partie = theme.creerPartie(etat, null);//gerer exception
        ihm.setTheme(themestr);
        int index=1; //
        int numPiece=1;
        String nom = ihm.choixNom();//choix nom du joueur
        String typePerso = ihm.choixPersonnage(themestr);
        Personnage perso = partie.creerPersonnage(typePerso, nom);
        partie.setPersonnage(perso);
        String s=perso.afficherPerso();
        ihm.afficher(s);
        partie.initialiserInventaire();
        partie.ajouterDonjon(partie.creerDonjonFacile());//CREATION DONJON DE 1 PIECE
        partie.ajouterDonjon(partie.creerDonjonMoyen());//CREATION DONJON DE 2 PIECES
        partie.ajouterDonjon(partie.creerDonjonDifficile());//CREATION DONJON DE 3 PIECES
        while (!(partie.getEtat() instanceof FinPartie)) {
            if(partie.getEtat() instanceof Home){
                boolean choixHome = ihm.demanderActionHome();
                if(choixHome==false){
                    ihm.afficherInventaire(perso.getInventaire().getLesObjets());
                    if (!(perso.getInventaire().getLesObjets().isEmpty())){
                        boolean rester=true;
                        while(rester){
                            int  b=ihm.demanderActionInventaire();
                            if (b==1){
                                int choix= ihm.choisirObjet(perso.getInventaire().getLesObjets());
                                Objet o=perso.getInventaire().getLesObjets().get(choix);
                                o.utiliser(perso);
                                ihm.afficherUtilisation(perso.getInventaire().getLesObjets().get(choix).toString());
                                perso.getInventaire().getLesObjets().remove(choix);
                                ihm.afficher(perso.afficherPerso());
                            }
                            else if (b==2){
                                int choix= ihm.choisirObjet(perso.getInventaire().getLesObjets());
                                ihm.afficherJeter(perso.getInventaire().getLesObjets().get(choix).toString());
                                perso.getInventaire().getLesObjets().remove(choix);
                            }
                            else{
                                ihm.retourHome();
                                break;
                            }
                            if (perso.getInventaire().getLesObjets().isEmpty()){
                                ihm.afficherInventaire(perso.getInventaire().getLesObjets());
                                ihm.retourHome();
                                break;
                            }
                            ihm.afficherInventaire(perso.getInventaire().getLesObjets());
                            rester=ihm.continuerDansInventaire();
                            if (!rester){
                                ihm.retourHome();
                            }
                        }
                    }

                    else{
                        ihm.retourHome();
                    }
                }
                else {
                    partie.setEtat(new Combat());
                }
            } else if (partie.getEtat() instanceof Combat) {
                ihm.entrerDonjon(index);
                partie.setDonjonCourant(partie.getListeDonjon().get(0));
                Piece piece= partie.getDonjonCourant().getPieces().get(0);
                ihm.entrerPiece(numPiece);
                perso.setEtatPrecedent(new EtatPersonnage(perso));
                while ((!(piece.getLesEnnemis().isEmpty()) && (perso.getPv() >= 0) )) {
                    ihm.afficherCombat(perso, piece.getLesEnnemis(),piece.getLesObjets());
                    if ((piece.getLesObjets().isEmpty())||(perso.getInventaire().getLesObjets().size()==perso.getInventaire().getTaille())){
                        int i=ihm.demanderEnnemi(piece.getLesEnnemis());
                        int val=piece.getLesEnnemis().get(i).getPv();
                        perso.attaquer(piece.getLesEnnemis().get(i));
                        ihm.afficherDegat(piece.getLesEnnemis().get(i).toString(),+val-piece.getLesEnnemis().get(i).getPv());
                        ihm.afficherCombat(perso, piece.getLesEnnemis(),piece.getLesObjets());
                    }else {
                        if (ihm.choixActionCombat()) {
                            ihm.afficherEnnemi(piece.getLesEnnemis());
                            int i=ihm.demanderEnnemi(piece.getLesEnnemis());
                            int val=piece.getLesEnnemis().get(i).getPv();
                            perso.attaquer(piece.getLesEnnemis().get(i));
                            ihm.afficherDegat(piece.getLesEnnemis().get(i).toString(),+val-piece.getLesEnnemis().get(i).getPv());
                            ihm.afficherCombat(perso, piece.getLesEnnemis(),piece.getLesObjets());
                        }
                        else {
                            if (perso.getInventaire().estComplet()){
                                ihm.afficherInventaireComplet();
                            }
                            else {
                                ihm.afficherObjet(piece.getLesObjets());
                                int v=ihm.demanderObjet(piece.getLesObjets());
                                ihm.afficherObjetRammasse(piece.getLesObjets().get(v).toString());
                                perso.ramasserObjet(piece.getLesObjets().get(v));
                                piece.retireObjet(piece.getLesObjets().get(v));
                                ihm.afficherCombat(perso, piece.getLesEnnemis(),piece.getLesObjets());
                            }
                        }
                    }
                    piece.retirerEnnemi();
                    for (Map.Entry<Integer, Ennemi> ennemi : piece.getLesEnnemis().entrySet()){
                        perso.setPv(perso.getPv()-ennemi.getValue().getAttaque());
                        ihm.afficherAttaque(ennemi.getValue().toString(),ennemi.getValue().getAttaque());
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if((perso.getPv() <= 0)){
                    ihm.defaite();
                    if (!(perso.getNbRespawn()==0)){
                       boolean b= ihm.choixActionDefaite();
                       if (b==true){
                           perso.updateRespawn();
                           ihm.afficherNbRespawnRestant(perso.getNbRespawn());
                           perso.restaurerEtatPersonnage();//
                           partie.retourEnArriere(partie.getDonjonCourant().getTypeDonjon());// a faire
                       }
                       else{
                           partie.setEtat(partie.getEtat().perdu());
                           ihm.retourHome();
                           perso.reinitialiser();
                           perso.getInventaire().retirerObjetsConsommables();
                       }
                    }
                    else {
                        partie.setEtat(partie.getEtat().perdu());
                        ihm.retourHome();
                        perso.reinitialiser();
                        perso.getInventaire().retirerObjetsConsommables();
                    }
                }
                 else {
                     if (!partie.estTerminee()){
                         numPiece++;
                         partie.getDonjonCourant().getPieces().remove(0);
                         if (partie.getDonjonCourant().estTerminee()==false) {
                             int choixCombat = ihm.choixActionVictoire();
                             if (choixCombat == 3) {
                                 partie.setEtat(new Home());
                                 numPiece = 1;
                                 partie.getListeDonjon().set(0, partie.recreerDonjon(partie.getDonjonCourant()));//recreer le donjon;
                                 ihm.retourHome();
                             } else if (choixCombat == 2) {
                                 ihm.afficherInventaire(perso.getInventaire().getLesObjets());
                                 if (!(perso.getInventaire().getLesObjets().isEmpty())) {
                                     boolean r = true;
                                     while (r) {
                                         int b = ihm.demanderActionInventaire();
                                         if (b == 1) {
                                             int choix2= ihm.choisirObjet(perso.getInventaire().getLesObjets());
                                             Objet o = perso.getInventaire().getLesObjets().get(choix2);
                                             ihm.afficherUtilisation(perso.getInventaire().getLesObjets().get(choix2).toString());
                                             o.utiliser(perso);
                                             perso.getInventaire().getLesObjets().remove(choix2);
                                             ihm.afficher(perso.afficherPerso());
                                         } else if (b == 2) {
                                             int choix2 = ihm.choisirObjet(perso.getInventaire().getLesObjets());
                                             ihm.afficherJeter(perso.getInventaire().getLesObjets().get(choix2).toString());
                                             perso.getInventaire().getLesObjets().remove(choix2);
                                         }
                                         else {
                                             break;
                                         }
                                         if (perso.getInventaire().getLesObjets().isEmpty()){
                                             ihm.afficherInventaire(perso.getInventaire().getLesObjets());
                                             ihm.retourHome();
                                             break;
                                         }
                                         ihm.afficherInventaire(perso.getInventaire().getLesObjets());
                                         r = ihm.continuerDansInventaire();
                                    }
                                 }
                             }



                            }
                         else {
                             partie.getListeDonjon().remove(0);
                             if (!partie.estTerminee()){
                                 numPiece=1;
                                 ihm.Victoire();
                                 index++;
                                 partie.setEtat(new Home());
                                 ihm.retourHome();
                             }
                             else{
                             partie.setEtat(new FinPartie());
                             }
                         }
                     }
                 }
            }
            else {
                throw new IllegalStateException("Il y a une erreur, cet etat est inconnu: " + partie.getEtat());
            }
        }
        ihm.Victoire();
        ihm.feliciter();
        ihm.quitterJeu();
    }
}
