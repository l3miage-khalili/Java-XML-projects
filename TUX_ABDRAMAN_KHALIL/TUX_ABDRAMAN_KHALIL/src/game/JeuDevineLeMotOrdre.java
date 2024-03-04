/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Khalil
 */
public class JeuDevineLeMotOrdre extends Jeu {
    int nbLettresRestantes ;    // stocke le nombre de lettres restantes
    Chronometre chrono ;
    
    public JeuDevineLeMotOrdre() throws SAXException, IOException, ParserConfigurationException{
        super() ;
    }
    
    @Override
    protected void demarrePartie(Partie partie){
        // initialisation du chronometre
        switch(partie.getNiveau()){
            case 1:
                chrono = new Chronometre(15) ;
                chrono.start();
                break;
            case 2:
                chrono = new Chronometre(20) ;
                chrono.start();
                break;
            case 3:
                chrono = new Chronometre(30) ;
                chrono.start();
                break;
            case 4:
                chrono = new Chronometre(40) ;
                chrono.start();
                break;
            case 5:
                chrono = new Chronometre(50) ;
                chrono.start();
                break;
            default :
                chrono = new Chronometre(15) ;
                chrono.start();
        }
        // initialisation du nb des lettres restantes
        this.nbLettresRestantes = partie.getMot().length() ;
    }
    
    @Override
    protected void appliqueRegles(Partie partie){
        String wToGet = partie.getMot() ;   // Mot à trouver
        int sizeMot = wToGet.length() ;     // Taille du mot à trouver
        int iFirstLetter = sizeMot - this.nbLettresRestantes ;  // indice de la 1ère lettre à trouver
        char c ;    // lettre(char) correspondant à la lettre (objet) à supprimer
        // S'il n'a pas reussi à collecter toutes les lettres dans le temps imparti
        if(!chrono.remainsTime() && this.nbLettresRestantes > 0){
            partie.setFini(true);
        }
        else{   // Il lui reste du temps
            if(this.nbLettresRestantes == 0){   // Plus de lettre à trouver
                partie.setFini(true);
            }
            else{   // Des lettres à collecter
                if(tuxTrouveLetter(iFirstLetter)){  // Si tux trouve la bonne lettre
                    this.env.removeObject(this.letters.get(iFirstLetter));
                    // On diminue le nb des lettres restantes
                    this.nbLettresRestantes-- ;
                }
            }
        }

    }
    
    @Override
    protected void terminePartie(Partie partie){
        // On enregistre dans la partie le temps mis pour collecter les lettres
        partie.setTemps(this.chrono.getTime());
        
        // On enregistre dans la partie le pourcentage des lettres trouvées
        partie.setTrouve(this.nbLettresRestantes);
        
        // On arrête le Chronomètre
        chrono.stop();
    }
    
    // renvoie true si la première lettre de la liste de lettres (restantes) du mot est en contact avec le personnage tux
    public boolean tuxTrouveLetter(int indice) {
        return collision(this.letters.get(indice)) ;
    }

}
