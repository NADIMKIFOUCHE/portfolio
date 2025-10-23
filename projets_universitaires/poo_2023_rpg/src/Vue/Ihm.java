package Vue;

import Modele.Ennemi;
import Modele.Objet;
import Modele.Personnage;

import java.util.Map;
import java.util.Scanner;

public class Ihm {
    private String theme;

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Ihm(String theme) {
        this.theme = theme;
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public String choixTheme(){
        String res="";
        Scanner sc= new Scanner(System.in);
        do {
            System.out.println("Veuillez choisir un thème entre préhistoire et fantastique");
            res=sc.next();
        }while(!(res.equalsIgnoreCase("Prehistoire")||(res.equals("Préhistoire"))
                ||res.equalsIgnoreCase("fantastique")||res.equals("préhistoire")));
        return res;
    }
    public String choixPersonnage(String s){
        Scanner sc= new Scanner(System.in);
        String res="";
        do {
            if(s.equalsIgnoreCase("Prehistoire")||s.equals("Préhistoire")||s.equals("préhistoire")){
                System.out.println("Veuillez choisir une classe de personnage,tapez C pour chasseur ou H pour Homme des Cavernes");
                res= sc.nextLine();
            }
            else if(s.equalsIgnoreCase("Fantastique")){
                System.out.println("Veuillez choisir une classe de personnage, tapez M pour mage ou P pour Paladin");
                res=sc.nextLine();
            }
        }while (!(res.equalsIgnoreCase("C")||res.equalsIgnoreCase("H")
                ||res.equalsIgnoreCase("M")||res.equalsIgnoreCase("P")));
        return res;
    }
    public String choixNom(){
        System.out.println("Veuillez choisir un Pseudo");
        Scanner sc= new Scanner(System.in);
        String res= sc.next();
        return res;
    }


    public void afficherInventaire(Map<Integer,Objet> lesObjets) {
        System.out.println(ANSI_YELLOW_BACKGROUND+"Objets dans l'inventaire:"+ANSI_RESET);
        if (lesObjets.size()==0){
            System.out.println(ANSI_BLUE +"Rien dans l'inventaire pour l'instant"+ ANSI_RESET);
        }
        for (Map.Entry<Integer, Objet> objet : lesObjets.entrySet()) {
            Integer cle = objet.getKey();
            Objet valeur = objet.getValue();
            System.out.println( ANSI_BLUE  +cle+" "+valeur+ ANSI_RESET);
        }
    }

    public int demanderActionInventaire(){
        Scanner sc = new Scanner(System.in);
        int res;
        do {
            System.out.println("Tapez 1 pour utiliser un objet de l'inventaire, 2 pour jeter un objet ou 3 pour quitter");
            if (sc.hasNextInt()){
                res= sc.nextInt();
            }
            else {
                System.out.println("Veuillez saisir un entier");
                sc.next();
                res = -1;
            }
        }while(!(res==1 || res ==2|| res==3));
        return res;
    }


    public int choisirObjet(Map<Integer,Objet> lesObjets){
        Scanner sc = new Scanner(System.in);
        int res;
        do {
            System.out.println("Saisissez le numéro de l'objet");
            if (sc.hasNextInt()){
                res= sc.nextInt();
            }
            else {
                System.out.println("Veuillez saisir un entier");
                sc.next();
                res = -1;
            }
        }while(!lesObjets.containsKey(res));
        return res;
    }

    public boolean choixActionCombat(){
        String res="";
        Scanner sc= new Scanner(System.in);
        do {
            System.out.println("A vous de jouer, tapez r pour ramasser un objet ou a pour attaquer un ennemi");
            res=sc.next();
        }while(!(res.equalsIgnoreCase("r")||res.equalsIgnoreCase("a")));
        if (res.equalsIgnoreCase("r")){
            return false;
        } else if (res.equalsIgnoreCase("a")) {
            return true;
        }
        return false;
    }
    public void afficher(String s){
        System.out.println(s);
    }
    public int demanderEnnemi(Map<Integer,Ennemi>lesEnnemis){
        int res;
        Scanner sc= new Scanner(System.in);
        do{
            System.out.println("Tapez le numéro de l'ennemi que vous souhaitez attaquer");
            if (sc.hasNextInt()){
                res= sc.nextInt();
            }
            else {
                System.out.println("Veuillez saisir un entier");
                sc.next();
                res=-1;
            }

        }while(!lesEnnemis.containsKey(res) );
        return res;
    }
    public Boolean demanderActionHome(){
        Scanner sc=new Scanner(System.in);
        String res="";
        do {
            System.out.println("Tapez I pour ouvrir l'inventaire ou E pour entrer dans le donjon");
             res=sc.nextLine();
        }while(!(res.equalsIgnoreCase("I")||res.equalsIgnoreCase("E")));
        if (res.equalsIgnoreCase("I"))
            return false;
        else if (res.equalsIgnoreCase("E")) {
            return true;
        }
        return false;
    }
    public int demanderObjet(Map<Integer,Objet>lesObjets){
        int res=0;
        Scanner sc=new Scanner(System.in);
        do {
            System.out.println("Tapez l'indice de l'objet que vous voulez ramasser");
            if (sc.hasNextInt()){
                res= sc.nextInt();
            }
            else {
                sc.next();
                res=-1;
            }
        }while (!(lesObjets.containsKey(res)));
        return res;
    }
    public void entrerDonjon(int i){
        System.out.println("Vous entrez dans le donjon "+i);
    }

    public void entrerPiece(int i){
        System.out.println("Vous entrez dans la piece "+i);
    }
    public void afficherEnnemi(Map<Integer,Ennemi> lesEnnemis){
        for (Map.Entry<Integer, Ennemi> ennemi: lesEnnemis.entrySet()) {
            Integer cle = ennemi.getKey();
            Ennemi valeur = ennemi.getValue();
            System.out.print( ANSI_RED+cle+" "+valeur+" "+valeur.getPv()+"pv" +ANSI_RESET+'\t'+'\t'+'\t');

        }
        System.out.println();
    }
    public void afficherObjet(Map<Integer,Objet>lesObjets){
        for (Map.Entry<Integer, Objet> objet : lesObjets.entrySet()) {
            Integer cle = objet.getKey();
            Objet valeur = objet.getValue();
            System.out.print( ANSI_BLUE +cle+" "+valeur+ ANSI_RESET+'\t'+'\t'+'\t'+'\t');
        }
        System.out.println();
    }
    public void afficherCombat(Personnage p, Map<Integer,Ennemi> lesEnnemis, Map<Integer,Objet>lesObjets){
        System.out.println();
        System.out.println(ANSI_GREEN+p.getNom()+" "+p.getPv()+" "+"pv"+ANSI_RESET);
        afficherEnnemi(lesEnnemis);
        afficherObjet(lesObjets);
        System.out.println();
    }
    public void afficherAttaque(String a1, int v){
        System.out.println(ANSI_RED+ a1 +" attaque et vous fait "+v+" de dégats"+ANSI_RESET);
    }

    public void afficherDegat(String a1, int v){
        System.out.println(ANSI_GREEN+ "Vous infligez "+v+" de dégats à "+a1+ANSI_RESET);
    }
    public void afficherObjetRammasse(String objet){
        System.out.println(ANSI_GREEN+"Vous avez ramassé : "+ objet+ANSI_RESET);
    }

    public void Victoire(){
        System.out.println(ANSI_GREEN_BACKGROUND+"Victoire !!!!"+ANSI_RESET);
        System.out.println(ANSI_GREEN_BACKGROUND+"Félicitation vous avez terminée le donjon ! "+ANSI_RESET);
    }
    public int choixActionVictoire(){
        System.out.println(ANSI_GREEN+"Bravo vous avez terminez la piece !!"+ANSI_RESET);
        System.out.println("Trois options se présentent à vous :  avancer vers la prochaine piece, ouvrir l'inventaire ou quitter le donjon");
        int res;
        Scanner sc= new Scanner(System.in);
        do {
            System.out.println("Tapez 1 pour avancer vers la prochaine pièce, 2 pour ouvrir l'inventaire ou 3 pour quitter le donjon");
            if (sc.hasNextInt()){
                res= sc.nextInt();
            }
            else {
                System.out.println("Veuillez saisir un entier");
                sc.next();
                res = -1;
            }
        }while(!((res==1) ||(res==2) || (res==3)));
        return res;
    }
    public boolean continuerDansInventaire(){
        String res="";
        Scanner sc= new Scanner(System.in);
        do {
            System.out.println("Voulez vous rester dans l'inventaire ?");
            System.out.println("Tapez oui pour rester ou  non pour quitter");
            res=sc.next();
        }while(!(res.equalsIgnoreCase("oui")||res.equalsIgnoreCase("non")));
        if (res.equalsIgnoreCase("non")){
            return false;
        } else if (res.equalsIgnoreCase("oui")) {
            return true;
        }
        return false;
    }

    public void afficherUtilisation(String s){
        System.out.println(ANSI_BLUE+"Vous avez utilisé : "+s+ANSI_RESET);
    }
    public void afficherJeter(String s){
        System.out.println(ANSI_BLUE+"Vous avez jeté : "+s+ANSI_RESET);
    }
    public void defaite(){
        System.out.println(ANSI_RED_BACKGROUND+"Défaite..."+ANSI_RESET);
    }
    public void retourHome(){
        System.out.println(ANSI_BLUE+" Vous retournez au menu principal"+ANSI_RESET);
    }
    public boolean choixActionDefaite(){
        System.out.println("Vous avez perdu, deux options se présentent à vous : retourner dans le passé ou quitter le donjon");
        int res;
        Scanner sc= new Scanner(System.in);
        do {
            System.out.println("Tapez 1 pour retourner en arrière ou 2 pour quitter le donjon");
            res=sc.nextInt();
        }while(!(res==1 ||res==2));
        if (res==1){
            return true;
        }
        else
            return false;
    }
    public void feliciter(){
        System.out.println(ANSI_GREEN_BACKGROUND+"Félicitation , vous avez terminé le jeu"+ ANSI_RESET);
    }

    public void afficherNbRespawnRestant(int a) {
        System.out.println(ANSI_BLUE+"Il vous reste "+a+" retour en arrière"+ANSI_RESET);
    }
    public void quitterJeu(){
        System.out.println(ANSI_BLACK_BACKGROUND+"Vous quittez le jeu"+ANSI_RESET);
    }

    public void afficherInventaireComplet() {
        System.out.println(ANSI_RED_BACKGROUND+
                " Vous ne pouvez pas ajouter d'élément à votre inventaire car il est plein"+ ANSI_RESET);
    }
}
