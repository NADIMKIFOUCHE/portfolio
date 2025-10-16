package Modele;


import java.util.HashMap;
import java.util.Map;


public class Inventaire {
    private Map<Integer,Objet> LesObjets;
    private final int taille=10;
    public Inventaire() {
        LesObjets = new HashMap<>();
    }

    public int getTaille() {
        return taille;
    }


    public Map<Integer,Objet> getLesObjets() {
        return LesObjets;
    }
    public void setLesObjets(Map<Integer,Objet> lesObjets) {
        LesObjets = lesObjets;
    }
    public void ajouterObjet(Objet objet) {
        if (!(estComplet())){
            int i= LesObjets.size()+1;
            LesObjets.put(i,objet);
        }

    }

    @Override
    public String toString() {
        if(LesObjets.isEmpty()){
            return " Inventaire vide ";
        }
        String s=" | ";
        for (Map.Entry<Integer,Objet> o: LesObjets.entrySet()){
            s+=o.getKey()+" "+o.getValue()+" | ";
        }
        return s;
    }
    public boolean estComplet(){
        if (LesObjets.size()==10){
            return true;
        }
        return false;
    }

    public void retirerObjetsConsommables() {
        for (Map.Entry<Integer,Objet> o : LesObjets.entrySet()){
            if (o.getValue().estConsommable()){
                LesObjets.remove(o.getKey());
            }
        }
    }
}
