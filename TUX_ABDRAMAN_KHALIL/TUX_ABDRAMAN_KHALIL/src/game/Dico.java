/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.Random;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Khalil
 */
public class Dico extends DefaultHandler {
    // Les attributs
    
    // Document DOM du dictionnaire
    private Document doc ;
    
    // Liste des mots de niveau 1
    private ArrayList<String> listeNiveau1 ;
    // Liste des mots de niveau 2
    private ArrayList<String> listeNiveau2 ;
    // Liste des mots de niveau 3
    private ArrayList<String> listeNiveau3 ;
    // Liste des mots de niveau 4
    private ArrayList<String> listeNiveau4 ;
    // Liste des mots de niveau 5
    private ArrayList<String> listeNiveau5 ;
    
    // chemin vers le dictionnaire xml
    private String cheminFichierDico ;
    
    // stocke le texte du XML
    private StringBuffer buffer ;
    // Niveau d'un mot
    private int niveau ;
    
    // L'element courant
    private int state ;
    // Etats determinant les elements du doc xml
    final int START = 0,
            DICTIONNAIRE = 1,
            MOTS = 2,
            MOT = 3,
            OTHER = 4 ;
    
    
    // Les methodes
    
    // Le constructeur
    public Dico(String cheminFichierDico){
        super() ;
        this.cheminFichierDico = cheminFichierDico ;
        listeNiveau1 = new ArrayList<String>() ;
        listeNiveau2 = new ArrayList<String>() ;
        listeNiveau3 = new ArrayList<String>() ;
        listeNiveau4 = new ArrayList<String>() ;
        listeNiveau5 = new ArrayList<String>() ;
    }
    
    // Renvoit un mot au hasard de la liste des mots en fonction du niveau donné
    public String getMotDepuisListeNiveaux(int niveau){
        String s = "" ;
        // On verifie que le niveau passé en param est valide
        switch(verifieNiveau(niveau)){
            case 5 :
                // renvoit un mot de niveau 5
                s = getMotDepuisListe(this.listeNiveau5) ;
                break ;
            case 4 :
                // renvoit un mot de niveau 4
                s = getMotDepuisListe(this.listeNiveau4) ;
                break ;
            case 3 :
                // renvoit un mot de niveau 3
                s = getMotDepuisListe(this.listeNiveau3) ;
                break ;
            case 2 :
                // renvoit un mot de niveau 2
                s = getMotDepuisListe(this.listeNiveau2) ;
                break ;
            case 1 :
                // renvoit un mot de niveau 1
                s = getMotDepuisListe(this.listeNiveau1) ;
                break ;
            default :   // La methode verifieNiveau(niveau) renvoit forcement un entier entre 1 et 5
        }
        return s ;
    }
    
    // Ajoute le mot en fonction du niveau
    public void ajouteMotADico(int niveau, String mot){
        // On verifie que le niveau passé en param est valide
        switch(verifieNiveau(niveau)){
            case 1 :
                // ajoute le mot dans la liste des mots de niveau 1
                listeNiveau1.add(listeNiveau1.size(), mot);
                break ;
            case 2 :
                // ajoute le mot dans la liste des mots de niveau 2
                listeNiveau2.add(listeNiveau2.size(), mot);
                break ;
            case 3 :
                // ajoute le mot dans la liste des mots de niveau 3
                listeNiveau3.add(listeNiveau3.size(), mot);
                break ;
            case 4 :
                // ajoute le mot dans la liste des mots de niveau 4
                listeNiveau4.add(listeNiveau4.size(), mot);
                break ;
            case 5 :
                // ajoute le mot dans la liste des mots de niveau 5
                listeNiveau5.add(listeNiveau5.size(), mot);
                break ;
            default :   // La methode verifieNiveau(niveau) renvoit forcement un entier entre 1 et 5
        }
    }

    public String getCheminFichierDico() {
        return cheminFichierDico;
    }
    
    // Verifie que le niveau passé en param est valide
    // Sinon le met à 1
    private int verifieNiveau(int niveau){
        int n ;
        if(niveau < 1 || niveau > 5){
            // Si le niveau est invalide, alors on envoit 1 pour ensuite renvoyer un mot de niveau 1
            n = 1 ;
        }
        else{
            // Sinon on envoit le niveau demandé pour envoyer plus tard un mot de ce niveau
            n = niveau ;
        }
        return n ;
    }
    
    // Renvoit au hasard un mot depuis la liste passé en param
    // Si la liste est vide elle renvoit le mot "Bon" 
    private String getMotDepuisListe(ArrayList<String> list){
        String motExtrait ;
        if(list.isEmpty()){
            // Liste vide : On renvoit le mot "vide"
            motExtrait = "vide" ;
        }
        else{
            // Liste non vide
            // On génère un indice aléatoire
            Random random = new Random() ;
            int indiceAleatoire  = random.nextInt(list.size()) ;
            motExtrait = list.get(indiceAleatoire);
        }
        return motExtrait ;
    }
    
    
    
    // Parser SAX du document
    public void lireDictionnaire() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory fabrique = SAXParserFactory.newInstance(); 
        SAXParser parseur = fabrique.newSAXParser();
        File fichier = new File(this.cheminFichierDico);
        parseur.parse(fichier, this);
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch(state){  // L'element precedant
            case START :
                if(qName.equals("dictionnaire")){
                    // On met à jour l'etat
                    state = DICTIONNAIRE ;
                }
                break ;
            case DICTIONNAIRE :
                if(qName.equals("mots")){
                    // On met à jour l'etat
                    state = MOTS ;
                }
                break ;
            case MOTS :
                if(qName.equals("mot")){
                    // On met à jour l'etat
                    state = MOT ;
                    // On recupère le niveau
                    this.niveau = Integer.parseInt(attributes.getValue(0)) ;
                    // On initialise le buffer
                    this.buffer = new StringBuffer() ;
                }
                break ;
            default :
                break ;
        }
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch(state){
            case DICTIONNAIRE :
                // On sort de l'elemet dictionnaire
                state = OTHER ;
                break ;
            case MOTS :
                // On sort de l'elemet mots, on revient dans dictionnaire
                state = DICTIONNAIRE ;
                break ;
            case MOT :
                // Fin de l'elemet mot, on rajoute les infos recuperées dans l'arrayList des mots
                ajouteMotADico(this.niveau,this.buffer.toString());
                this.buffer = null ;
                // on revient dans mots
                state = MOTS ;
                break ;
            default :
                break ;
        }
    }
        
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String s = "" ;
        
        for(int i=start; i< start + length; i++){
            s += ch[i] ;
        }
        
        switch(state){
            case MOT :
                // Unique element contenant du "text"
                this.buffer.append(s) ;
                break ;
            default :
                this.buffer = null ;
                break ;
        }

    }

    @Override
    public void startDocument() throws SAXException {
        // le parseur entre dans le document
        state = START ;
    }

    @Override
    public void endDocument() throws SAXException {
        // le parseur arrive à la fin du document
        state = OTHER ;
    }
    
    // ----------------------------------------------
    // Méthodes d'édition du dictionnaire via DOM
    // ----------------------------------------------
    
    //  Parseur DOM du document
    public void lireDictionnaireDOM(String path, String filename){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance() ;
        try{
            DocumentBuilder builder = factory.newDocumentBuilder() ;
            this.doc = builder.parse(path + "/" + filename) ;
            
            // On récupère les mots dans un NodeList
            NodeList motNL = doc.getElementsByTagName("mot");
            
            // Taille du NodeList
            int nbMots = motNL.getLength(); // Nbre des mots du dico
            
            String mot ;    // Un mot du dictionaire
            int niveau ;    // le niveau de ce mot
            
            // Ajout des mots aux arrayList de mot
            for(int i=0; i<nbMots; i++){
                // Traitement d'un mot
                Element motElt = (Element) motNL.item(i);   // Récupère un mot
                mot = motElt.getTextContent();  // Récupère sa valeur
                niveau = Integer.parseInt(motElt.getAttribute("niveau"));   // récupère son niveau
                ajouteMotADico(niveau,mot); // L'ajoute à l'arrayList corrspondant
            }
            
        } catch (Exception e){
            e.printStackTrace();
        }
        
    }
    
    // ajoute un mot et son niveau dans le dictionnaire en éditant le document DOM
    public void ajouterMot(String mot, int niveau){
        // Récupère l'element mots
        Element motsElt = (Element) this.doc.getElementsByTagName("mots").item(0) ;
        
        // Crée un element mot
        Element motElt = this.doc.createElement("mot") ;
        // Ajoute l'attribut niveau
        motElt.setAttribute("niveau", String.valueOf(niveau));
        motElt.setTextContent(mot);
        
        // Ajoute l'element mot comme sous-element de mots
        motsElt.appendChild(motElt) ;
    }
    
}
