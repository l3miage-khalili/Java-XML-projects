/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

/**
 *
 * @author Khalil
 */
public class TestDico {
    
    public static void main(String[] args) {
        // le mot recupere du dictionnaire
        String motRecupere ;
        
        // instance d'un dictionnaire
        Dico dico = new Dico("../Tux/src/xml/dico.xml");
        
        // Lecture du dictionnaire depuis le fichier dico.xml
        dico.lireDictionnaireDOM("../Tux/src/xml", "dico.xml");
        
        /// Tests de lecture ///
        
        // Plusieurs mots de niveau 1
        motRecupere = dico.getMotDepuisListeNiveaux(1);
        System.out.println("Un mot de niveau 1 : " + motRecupere);
        motRecupere = dico.getMotDepuisListeNiveaux(1);
        System.out.println("Un mot de niveau 1 : " + motRecupere);
        motRecupere = dico.getMotDepuisListeNiveaux(1);
        System.out.println("Un mot de niveau 1 : " + motRecupere);
        
        // un mot de niveau 2
        motRecupere = dico.getMotDepuisListeNiveaux(2);
        System.out.println("Un mot de niveau 2 : " + motRecupere);
        
        // un mot de niveau 3
        motRecupere = dico.getMotDepuisListeNiveaux(3);
        System.out.println("Un mot de niveau 3 : " + motRecupere);
        
        // un mot de niveau 4
        motRecupere = dico.getMotDepuisListeNiveaux(4);
        System.out.println("Un mot de niveau 4 : " + motRecupere);
        
        // Un mot de niveau 5
        motRecupere = dico.getMotDepuisListeNiveaux(5);
        System.out.println("Un mot de niveau 5 : " + motRecupere);
        
        // Liste 6 non valide
        motRecupere = dico.getMotDepuisListeNiveaux(6);
        System.out.println("Un mot de niveau non valide (doit afficher un mot de niveau 1) : " + motRecupere); // doit afficher un mot de niveau 1
    }
}
