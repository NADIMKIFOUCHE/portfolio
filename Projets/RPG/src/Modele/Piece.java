package Modele;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Piece {
    private Map<Integer, Objet> lesObjetsSauvegardes;
    private Map<Integer, Ennemi> lesEnnemisSauvegardes;

    private Map<Integer, Objet> lesObjets;
    private Map<Integer, Ennemi> lesEnnemis;

    public Piece() {
        // Initialisation des collections
        this.lesObjets = new HashMap<>();
        this.lesEnnemis = new HashMap<>();

    }


    public void ajouterObjet(int id , Objet objet) {
        lesObjets.put(id, objet);
    }

    public void ajouterEnnemi(int id, Ennemi ennemi) {
        lesEnnemis.put( id, ennemi);
    }

    public Map<Integer, Objet> getLesObjets() {
        return lesObjets;
    }

    public void setLesObjets(Map<Integer, Objet> lesObjets) {
        this.lesObjets = lesObjets;
    }

    public Map<Integer, Ennemi> getLesEnnemis() {
        return lesEnnemis;
    }

    public void setLesEnnemis(Map<Integer, Ennemi> lesEnnemis) {
        this.lesEnnemis = lesEnnemis;
    }

    public Map<Integer, Objet> getLesObjetsSauvegardees() {
        return lesObjetsSauvegardes;
    }

    public void setLesObjetsSauvegardees(Map<Integer, Objet> lesObjetsSauvegardees) {
        this.lesObjetsSauvegardes = lesObjetsSauvegardees;
    }

    public Map<Integer, Ennemi> getLesEnnemisSauvegardes() {
        return lesEnnemisSauvegardes;
    }

    public void setLesEnnemisSauvegardes(Map<Integer, Ennemi> lesEnnemisSauvegardes) {
        this.lesEnnemisSauvegardes = lesEnnemisSauvegardes;
    }

    public void retirerEnnemi(){
        Map<Integer,Ennemi> lesEnnemisRestants = new HashMap<>();
        for (Map.Entry<Integer, Ennemi> e : lesEnnemis.entrySet()){
            if(!(e.getValue().getPv()<=0)){
                lesEnnemisRestants.put(e.getKey(),e.getValue());
            }
        }
        lesEnnemis=lesEnnemisRestants;
    }
    public void retireObjet(Objet o){
        Map<Integer,Objet> lesObjetsRestants=new HashMap<>();
        for (Map.Entry<Integer, Objet> objet : lesObjets.entrySet()){
            if(!(objet.getValue()==o)){
                lesObjetsRestants.put(objet.getKey(), objet.getValue());
            }
        }
        lesObjets=lesObjetsRestants;
    }

}


