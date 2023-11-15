/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package simulation;

// import de la classe Coordonnee 
import trajectoires.Coordonnee ;
// import de la classe Etape
import trajectoires.Etape;

/**
 *
 * @author Khalil
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // --
        // -- Création d'une première Coordonnée pos1
        // --

        // 1. déclaration d'une variable de type référence
        Coordonnee pos1;
        
        // 2. instanciation utilisant le constructeur par défaut
        pos1 = new Coordonnee();

        // 3. remplissage : initialisation de la première Coordonnée avec les valeurs (0.1,0.0,-1.0)
        pos1.set(1,1,1);

        // --
        // -- Création d'une deuxième Coordonnée pos2
        // --

        // 1. déclaration
        Coordonnee pos2;

        // 2. instanciation utilisant le constructeur qui initialise la Coordonnée avec les valeurs (1.0, 1.0, 0.01)
        pos2 = new Coordonnee(1.0, 1.0, 0.01);

        // 3. modification : re-initialisation de la deuxième Coordonnée avec les valeurs (1.0, 1.0, 0.0)
        pos2.set(2,2,2);

        // affichage des deux coordonnées dans la fenêtre de résultats
        System.out.println("Coordonnée 1 :" + pos1.toString() );
        System.out.println("Coordonnée 2 :" + pos2.toString() );
        
        //Declaration d'une variable de type reference : étape
        Etape etape ;
        
        // instanciation de etape
        etape = new Etape(pos1,pos2) ;
        
        // affichage de l'etape dans la fenêtre de résultats
        System.out.println("étape :" + etape.toString() );
        
        // Affichage de la distance de cette étape
        System.out.println("distance : " + etape.distance());
        
        
    }
    
}
