/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trajectoires;

/**
 *
 * @author Khalil
 */
public class Etape {
    
    // attribut depart
    Coordonnee depart ;
    
    // attribut depart
    Coordonnee arrivee ;
    
    // Setter de l'étape
    public void set(Coordonnee depart, Coordonnee arrivee) {
        this.depart = depart ;
        this.arrivee = arrivee ;
    }

    // Getter du depart
    public Coordonnee getDepart() {
        return depart;
    }

    // Setter du depart
    public void setDepart(Coordonnee depart) {
        this.depart = depart;
    }
    
    
    // Getter d'arrivee
    public Coordonnee getArrivee() {
        return arrivee;
    }

    // Setter d'arrivee
    public void setArrivee(Coordonnee arrivee) {
        this.arrivee = arrivee;
    }
    
    // Constructeur de Etape
    public Etape(Coordonnee depart, Coordonnee arrivee) {
        set(depart,arrivee) ;
    }
    
    
    // Calcul la distance entre depart et arrivee
    public double distance() {
        
        double diffX = arrivee.getX() - depart.getX() ;
        
        double diffY = arrivee.getY() - depart.getY() ;
        
        double diffZ = arrivee.getZ() - depart.getZ() ;
        
        double dist = Math.sqrt(Math.pow(diffX,2) + Math.pow(diffY,2) + Math.pow(diffZ,2)) ;
        
        return dist ;
    }
    
    @Override
    public String toString() {
        return depart.toString() + "->" + arrivee.toString() ;
    }
}
