/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Khalil
 */
public class Profil {
    // Les attributs
    
    // Nom du joueur
    private String nom ;
    // Avatar du joueur
    private String avatar ;
    // date de naissance du joueur
    private String dateNaissance ;
    // Liste des parties
    private ArrayList<Partie> parties ;
    // Nombre des parties du profil existant
    private int nbPartiesExist = 0 ;
    
    public Document _doc;
    
    
    //Constructeur par defaut
    public Profil(String nom, String dateNaissance, String avatar) throws ParserConfigurationException {
        this.nom = nom ;
        this.dateNaissance = dateNaissance ;
        this.avatar = avatar ;
        this.parties = new ArrayList<Partie>() ;
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance() ;
        DocumentBuilder builder = factory.newDocumentBuilder() ;
        this._doc = builder.newDocument() ;
    }

    // lit un fichier XML, parse le document et utilise le document DOM 
    // pour extraire les données nécessaires à la récupération des valeurs 
    // du profil et des parties existantes
    public Profil(String filename) throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance() ;
        DocumentBuilder builder = factory.newDocumentBuilder() ;
        this._doc = builder.parse(filename) ;
        
        // Instanciation de l'arrayList parties
        this.parties = new ArrayList<Partie>() ;
        
        // La racine profil
        Element profilElt = (Element) _doc.getElementsByTagName("profil").item(0) ;
        
        // L'element "nom du profil"
        Element nomElt = (Element) profilElt.getElementsByTagName("nom").item(0) ;
        // initialisation de l'attribut nom
        this.nom = nomElt.getTextContent() ;
        
        // L'element "avatar du profil"
        Element avatarElt = (Element) profilElt.getElementsByTagName("avatar").item(0) ;
        // initialisation de l'attribut avatar
        this.avatar = avatarElt.getTextContent() ;
        
        // L'element "date de naissance du profil"
        Element dateElt = (Element) profilElt.getElementsByTagName("anniversaire").item(0) ;
        this.dateNaissance = xmlDateToProfileDate(dateElt.getTextContent()) ;
        
        // Les elements parties
        NodeList partieElts = profilElt.getElementsByTagName("partie") ;
        this.nbPartiesExist = partieElts.getLength() ;
        for(int i=0; i<nbPartiesExist; i++){
            // La partie courante
            Element PartieElt = (Element) partieElts.item(i) ;
            // Instanciation d'une partie à partir de la partie courante
            Partie partie = new Partie(PartieElt) ;
            // Rajout de la partie dans l'arrayliste parties
            parties.add(partie) ;
        }
    }
    
    
    // Sauvegarde un DOM en XML
    private void toXML(String nomFichier) {
        try {
            XMLUtil.DocumentTransform.writeDoc(_doc, nomFichier);
        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    /// Takes a date in profile format: dd/mm/yyyy and returns a date
    /// in XML format (i.e. ????-??-??)
    public static String profileDateToXmlDate(String profileDate) {
        String date;
        // Récupérer l'année
        date = profileDate.substring(profileDate.lastIndexOf("/") + 1, profileDate.length());
        date += "-";
        // Récupérer  le mois
        date += profileDate.substring(profileDate.indexOf("/") + 1, profileDate.lastIndexOf("/"));
        date += "-";
        // Récupérer le jour
        date += profileDate.substring(0, profileDate.indexOf("/"));

        return date;
    }
    
    // permet de rajouter à la liste des parties une Partie instanciée
    public void ajoutePartie(Partie p){
        this.parties.add(p) ;
    }
    
    // permet de sauvegarder le document DOM dans un fichier XML
    public void sauvegarder(String filename) throws TransformerConfigurationException, TransformerException, ParserConfigurationException, SAXException, IOException{    
        Element partiesElt = (Element) this._doc.getElementsByTagName("parties").item(0) ;
        for(int i=this.nbPartiesExist; i<parties.size(); i++){
            Partie p = parties.get(i) ;
            Element newPartie = this._doc.createElement("partie") ;
            String newDate = profileDateToXmlDate(p.getDate()) ;
            newPartie.setAttribute("date", newDate);
            newPartie.setAttribute("trouvé", p.getTrouve()+"%");
            Element newTemps = this._doc.createElement("temps") ;
            newTemps.setTextContent(String.valueOf(p.getTemps()));
            newPartie.appendChild(newTemps) ;
            Element newMot = this._doc.createElement("mot") ;
            newMot.setAttribute("niveau", String.valueOf(p.getNiveau())) ;
            newMot.setTextContent(p.getMot()) ;
            newPartie.appendChild(newMot) ;
            partiesElt.appendChild(newPartie) ;
        }
        toXML(filename) ;
    }
    
    // permet de sauvegarder un nouvau profil dans un fichier XML
    public void sauvegarderNewProfil(String filename) throws TransformerConfigurationException, TransformerException, ParserConfigurationException, SAXException, IOException{    
        
        // Creation de la racine
        Element newProfil = this._doc.createElement("profil") ;
        
        // Creation de l'element nom
        Element newNom = this._doc.createElement("nom") ;
        newNom.setTextContent(this.nom);
        newProfil.appendChild(newNom) ;
        
        // Creation de l'element avatar
        Element newAvatar = this._doc.createElement("avatar") ;
        newAvatar.setTextContent(this.avatar) ;
        newProfil.appendChild(newAvatar) ;
        
        // Creation de l'element anniversaire
        Element newAnniversaire = this._doc.createElement("anniversaire") ;
        newAnniversaire.setTextContent(this.dateNaissance) ;
        newProfil.appendChild(newAnniversaire) ;
        
        // Creation de l'element parties
        Element newParties = this._doc.createElement("parties") ;
        
        // Rajout des elements partie à l'element parties
        for(int i=0; i<parties.size(); i++){
            Partie p = parties.get(i) ;
            Element newPartie = this._doc.createElement("partie") ;
            String newDate = profileDateToXmlDate(p.getDate()) ;
            newPartie.setAttribute("date", newDate);
            newPartie.setAttribute("trouvé", p.getTrouve()+"%");
            Element newTemps = this._doc.createElement("temps") ;
            newTemps.setTextContent(String.valueOf(p.getTemps()));
            newPartie.appendChild(newTemps) ;
            Element newMot = this._doc.createElement("mot") ;
            newMot.setAttribute("niveau", String.valueOf(p.getNiveau())) ;
            newMot.setTextContent(p.getMot()) ;
            newPartie.appendChild(newMot) ;
            newParties.appendChild(newPartie) ;
        }
        
        // Rajout de l'element parties au profil
        newProfil.appendChild(newParties) ;
        
        // Rajout de la racine
        this._doc.appendChild(newProfil) ;
        
        toXML(filename) ;
    }
    
    public String getNom() {
        return nom;
    }

    public ArrayList<Partie> getParties() {
        return parties;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    // charge()
    public boolean charge(String nom){
        return this.nom.equals(nom) ;
    }
    
    // renvoie l'indice de la partie ayant le mot passé en param
    // Si une telle partie n'existe pas, alors on renvoit -1
    public int chargePartie(String mot){
        for(int i=0; i<parties.size(); i++){
            if(parties.get(i).getMot().equals(mot)){
                return i ;
            }
        }
        return -1 ;
    }
    
    @Override
    public String toString(){
        String s = "" ;
        for(Partie p : getParties()){
            s += p.toString() ;
        }
        return s ;
    }
    
}
