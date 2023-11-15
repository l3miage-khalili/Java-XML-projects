/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trajectoires;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Khalil
 */
public class Trajectoire {
    
    // attribut pos
    List<Coordonnee> pos ;
    
    // Constructeur par defaut
    public Trajectoire() {
        pos = new ArrayList<Coordonnee>() ;
    }
    
    // ajoute la Coordonnee passée en paramètre dans la liste
    boolean ajoute(Coordonnee pos) {
        this.pos.add(pos) ;
        return true ;
    }
    
    // ajoute l'Etape passée en paramètre dans la liste
    boolean ajoute(Etape etape) {
        // La trajectoire ne comporte aucune coordonnée
        if (this.pos.isEmpty()){
            // On ajoute la coordonnée du depart
            ajoute(etape.depart) ;
            // On ajoute la coordonnée d'arrivee
            ajoute(etape.arrivee) ;
            return true ;
        }
        // La trajectoire comporte au moins une coordonnée
        else {
            if(etape.depart.isEqual(this.pos.get(this.pos.size()-1))) {
                // On ajoute la coordonnée d'arrivee
                ajoute(etape.arrivee) ;
                return true ;
            }
            // la coordonnée de départ de l'étape ne correspond pas à la dernière coordonnée de la trajectoire
            else{
                return false ;
            }
        }
    }
    
    // calcule la distance parcourue selon la trajectoire
    public double distance(){
        double dist = 0.0 ;
        // Si la trajectoire comporte moins de 2 coordonnées (0 ou 1), la distance est nulle
        if(this.pos.size() < 2){
            return dist ;
        }
        // Il y a plus de 2 coordonnées
        else{
            for(int i=0; i < this.pos.size()-1; i++){
                for (int j=1; j < this.pos.size(); j++){
                    Etape etape = new Etape(this.pos.get(i),this.pos.get(j)) ;
                    dist = dist + etape.distance() ;
                }
            }
            return dist ;
        }
    }
    
    @Override
    public String toString() {
        String s = "";
        if (pos.size() == 0) {
            s += "Aucun point enregistré";
        } else        
        if (pos.size() == 1) {
            s += "Il n'y a qu'un point enregistré : "+pos.get(0).toString();
        } 
        else {
            Etape etape = new Etape(pos.get(0), pos.get(1));
            s += " [étape " + 1 + "] " + etape.toString();
            for (int i = 1; i < pos.size()-1; i++) {
                s += " [étape " + (i + 1) + "] ";
                etape.set(pos.get(i), pos.get(i + 1));
                s += etape.toString();
            }
        }
        return s;
    }
    
}
