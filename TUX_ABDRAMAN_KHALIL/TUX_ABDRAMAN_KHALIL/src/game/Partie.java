/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Khalil
 */
public class Partie {
    // Les attributs
    private String date ;   // date de la partie
    private String mot ;    // mot tiré aléatoirement dans le dictionnaire
    private int niveau ;    // niveau de jeu choisi
    private int trouve ;    // pourcentage des lettres trouvees
    private int temps ;     // temps en secondes qu'a duré la partie
    private boolean fini ;  // Indique si une partie est finie
    
    
    //Constructeur par defaut
    public Partie(String date, String mot, int niveau) {
        this.date = date ;
        this.mot = mot ;
        this.niveau = niveau ;
    }
    
    // Construction et Réinitialisation d'une Partie déjà faite et issue du XML
    public Partie(Element partieElt){
        // Recupération de la date
        this.date = xmlDateToProfileDate(partieElt.getAttribute("date")) ;
        // Recupération du noeud mot sous forme d'element
        Element motElt = (Element) partieElt.getElementsByTagName("mot").item(0) ;
        // Récupération de la valeur du mot
        this.mot = motElt.getTextContent();
        // Récupration du niveau
        this.niveau = Integer.parseInt(motElt.getAttribute("niveau")) ;
    }
    
    /// Takes a date in XML format (i.e. ????-??-??) and returns a date
    /// in profile format: dd/mm/yyyy
    public static String xmlDateToProfileDate(String xmlDate) {
        String date;
        // récupérer le jour
        date = xmlDate.substring(xmlDate.lastIndexOf("-") + 1, xmlDate.length());
        date += "/";
        // récupérer le mois
        date += xmlDate.substring(xmlDate.indexOf("-") + 1, xmlDate.lastIndexOf("-"));
        date += "/";
        // récupérer l'année
        date += xmlDate.substring(0, xmlDate.indexOf("-"));

        return date;
    }
    
    // crée le bloc XML représentant une partie à partir du paramètre doc
    // et renvoie  ce bloc en tant que Element
    
    public Element getPartie(Document doc){
        Element partieElt = doc.createElement("partie") ;
        partieElt.setAttribute(date, this.date);
        Element motElt = doc.createElement("mot") ;
        motElt.setAttribute("niveau", String.valueOf(this.niveau));
        motElt.setTextContent(this.mot);
        partieElt.appendChild(motElt) ;
        return partieElt ;
    }
    
    // Calcul du taux des lettres trouvées
    public void setTrouve(int nbLettresRestantes){
        // On determine le nb des lettres deja trouvees
        int nbLettresTrouvees = mot.length() - nbLettresRestantes ;
        if(nbLettresTrouvees < 0){
            nbLettresTrouvees = 0 ;
        }
        this.trouve = (int) ((double) nbLettresTrouvees / mot.length() * 100) ;
    }

    public int getTrouve() {
        return trouve;
    }

    public int getTemps() {
        return temps;
    }
    
    

    
    public void setTemps(int temps){
        this.temps = temps ;
    }

    public String getDate() {
        return date;
    }
    
    
    public int getNiveau(){
        return this.niveau ;
    }
    
    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public String getMot(){
        return this.mot ;
    }
    
    public void setMot(String mot){
        this.mot = mot ;
    }
    
    public void setDate(String date) {
        this.date = date;
    }

    public boolean isFini() {
        return fini;
    }

    public void setFini(boolean fini) {
        this.fini = fini;
    }
    
    
    
    @Override
    public String toString(){
        String s = "" ;
        s += "\n--------------------Partie jouée----------------------" ;
        s += "\nDate : " + date ;
        s += "\nMot à trouver : " + mot ;
        s += "\nNiveau : " + niveau ;
        s += "\nTrouvé : " + trouve + "%" ;
        s += "\nTemps mis : " + temps + "secondes";
        s += "\n-------------------------------------------------------" ;
        return s ;
    }
    
}
