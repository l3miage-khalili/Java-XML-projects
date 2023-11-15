/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trajectoires;

/**
 *
 * @author Khalil
 */
public class Coordonnee {
    
    private double x ;
    private double y ;
    private double z ;

    
    // Getter et Setter de x
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }
    
    // Getter et Setter de y
    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    // Getter et Setter de z
    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
    
    // Modifie les attributs x, y et z
    public void set(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    // Constructeur par defaut
    public Coordonnee(){
        set(0,0,0);
    }
    
    // Constructeur à 3 param
    public Coordonnee(double x, double y, double z){
        // methode set à 3 param pour initialiser les attributs
        set(x,y,z);
    }
    
    // Constructeur par copie
    public Coordonnee(Coordonnee c){
        // methode set à 3 param pour initialiser les attributs
        set(c.x,c.y,c.z);
    }
    
    @Override
    public String toString() {
        return "(" + x + "," + y + "," + z + ")" ;
    }
    
    public boolean isEqual(Coordonnee pos){
        return ((pos.x == x) && (pos.y == y) && (pos.z == z)) ;
    }
    
}
