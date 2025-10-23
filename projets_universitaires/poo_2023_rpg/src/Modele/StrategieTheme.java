package Modele;

import java.util.Scanner;

public class StrategieTheme {
    public Theme creerTheme(String a) {
        Theme theme=null;
        if (a.equalsIgnoreCase("Fantastique")) {
            theme = new Fantastique();
            return theme;
        }
        else if (a.equalsIgnoreCase("Prehistoire")||a.equalsIgnoreCase("Préhistoire")) {
           theme = new Prehistoire();
            return theme;
        }
        return theme;
    }
}
