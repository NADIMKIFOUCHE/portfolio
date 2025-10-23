package Modele;

import java.util.ArrayList;
import java.util.List;
public class Donjon {

    private int id;
    private List<Piece> lesPieces = new ArrayList<>();

    private int typeDonjon;
    private Piece PieceCourante;
    private boolean win = false;
    public Donjon() {
        this.lesPieces = new ArrayList<>();
    }

    public void ajouterPiece(Piece piece) {
        lesPieces.add(piece);
    }

    public List<Piece> getPieces() {
        return lesPieces;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPieces(List<Piece> pieces) {
        this.lesPieces = pieces;
    }

    public Piece getPieceCourante() {
        return PieceCourante;
    }

    public int getTypeDonjon() {
        return typeDonjon;
    }

    public void setTypeDonjon(int typeDonjon) {
        this.typeDonjon = typeDonjon;
    }

    public void setPieceCourante(Piece pieceCourante) {
        PieceCourante = pieceCourante;
    }

    public boolean estTerminee(){
        if(lesPieces.isEmpty()){
            return true;
        }
        return false;
    }
    public void avancer(){
        this.lesPieces.remove(0);
    }
}