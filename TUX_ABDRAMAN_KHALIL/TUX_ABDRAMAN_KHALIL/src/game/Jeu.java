/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import env3d.Env;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.lwjgl.input.Keyboard;
import org.xml.sax.SAXException;

/**
 *
 * @author gladen
 */
public abstract class Jeu {

    enum MENU_VAL {
        MENU_SORTIE, MENU_CONTINUE, MENU_JOUE
    }

    protected final Env env;
    private Tux tux;
    private final Room mainRoom;
    private final Room menuRoom;
    //private Letter letter;
    protected ArrayList<Letter> letters ; // Conteneur des lettres
    private Profil profil;
    private final Dico dico;
    protected EnvTextMap menuText;                         //text (affichage des texte du jeu)
    private String motATrouver ;    // Sert a afficher au joueur le mot à trouver
    private boolean newPlayer = false ;
    
    
    
    public Jeu() throws SAXException, IOException, ParserConfigurationException {

        // Crée un nouvel environnement
        env = new Env();

        // Instancie une Room
        mainRoom = new Room();

        // Instancie une autre Room pour les menus
        menuRoom = new Room();
        menuRoom.setTextureEast("textures/black.png");
        menuRoom.setTextureWest("textures/black.png");
        menuRoom.setTextureNorth("textures/black.png");
        menuRoom.setTextureBottom("textures/black.png");

        // Règle la camera
        env.setCameraXYZ(50, 60, 175);
        env.setCameraPitch(-20);

        // Désactive les contrôles par défaut
        env.setDefaultControl(false);
        
        // Instancie l'ArrayList des lettres
        letters = new ArrayList<Letter>();

        // Instancie un profil par défaut
        //profil = new Profil("kbg", "1234", "fire");
        
        profil = new Profil("./src/xml/profil.xml");
        
        // Dictionnaire
        dico = new Dico("./src/xml/dico.xml");

        // instancie le menuText
        menuText = new EnvTextMap(env);
        
        // Textes affichés à l'écran
        menuText.addText("Voulez vous ?", "Question", 200, 300);
        menuText.addText("1. Charger un profil de joueur existant ?", "Principal1", 250, 280);
        menuText.addText("Choisissez un nom de joueur : ", "NomJoueur", 200, 300);
        menuText.addText("Choisissez une année de naissance : ", "DateNaissance", 200, 300);
        menuText.addText("Choisissez un avatar (format png) : ", "avatarJoueur", 200, 300);
        
        menuText.addText("2. Créer un nouveau joueur ?", "Principal2", 250, 260);

        menuText.addText("1. Commencer une nouvelle partie ?", "Jeu1", 250, 280);
        menuText.addText("Niveau de la partie ? (1: Très Facile - 5: Très difficile) : ", "NiveauPartie", 150, 300);
        
        menuText.addText("2. Charger une partie existante ?", "Jeu2", 250, 260);
        menuText.addText("Mot de la partie ? : ", "MotPartie", 200, 300);
        
        menuText.addText("3. Ajouter une nouvelle partie ?", "Jeu3", 250, 240);
        
        
        menuText.addText("3. Sortir du jeu ?", "Principal3", 250, 240);
        menuText.addText("4. Sortir de ce jeu ?", "Jeu4", 250, 220);
        menuText.addText("5. Quitter le jeu ?", "Jeu5", 250, 200);
        
        menuText.addText("Voulez vous imprimer les parties réalisées ? : \n 1. Oui\n 2. Non\n", "ImprimeParties", 200, 300);
        
    }

    /**
     * Gère le menu principal
     *
     */
    public void execute() throws IOException, SAXException, TransformerException, TransformerConfigurationException, ParserConfigurationException {

        MENU_VAL mainLoop;
        
        mainLoop = MENU_VAL.MENU_SORTIE;
        do {
            mainLoop = menuPrincipal();
        } while (mainLoop != MENU_VAL.MENU_SORTIE);
        
        this.env.setDisplayStr("Au revoir !", 300, 30);
        
        // Détruit l'environnement et provoque la sortie du programme
        env.exit();
    }


    // fourni
    private String getNomJoueur() {
        String nomJoueur = "";
        menuText.getText("NomJoueur").display();
        nomJoueur = menuText.getText("NomJoueur").lire(true);
        menuText.getText("NomJoueur").clean();
        return nomJoueur;
    }
    
    // Demande la date de naissance du joueur
    private String getDateNaissance() {
        String date = "";
        menuText.getText("DateNaissance").display();
        date = menuText.getText("DateNaissance").lire(true);
        menuText.getText("DateNaissance").clean();
        return date;
    }
    
    // Demande l'avatar du joueur
    private String getAvatar() {
        String avatarJoueur = "";
        menuText.getText("avatarJoueur").display();
        avatarJoueur = menuText.getText("avatarJoueur").lire(true);
        menuText.getText("avatarJoueur").clean();
        return avatarJoueur;
    }
    
    // Lit le mot de la partie
    private String getMotPartie() {
        String motPartie = "";
        menuText.getText("MotPartie").display();
        motPartie = menuText.getText("MotPartie").lire(true);
        menuText.getText("MotPartie").clean();
        return motPartie;
    }
    
    // Lit le niveau de la partie
    private String getNiveauPartie() {
        String NiveauPartie = "";
        menuText.getText("NiveauPartie").display();
        NiveauPartie = menuText.getText("NiveauPartie").lire(true);
        menuText.getText("NiveauPartie").clean();
        return NiveauPartie;
    }
    
    // Lit la réponse du joueur s'il veut imprimer les parties réalisées
    private String getImprimeParties() {
        String reponse = "";
        menuText.getText("ImprimeParties").display();
        reponse = menuText.getText("ImprimeParties").lire(true);
        menuText.getText("ImprimeParties").clean();
        return reponse;
    }
    
    // Affiche Trouve ce mot : xxxxxxxxxx
    private void getTrouveMot() {
        menuText.getText("TrouveMot").display();
        env.advanceOneFrame();
        menuText.getText("TrouveMot").clean();
    }
    
    // Vérifie si une chaine contient un entier, utilisée à l'ajout d'une nouvelle partie 
    // pour vérifier que le mot saisi par le joueur est valide
    public static boolean contientEntier(String input) {
        Pattern pattern = Pattern.compile(".*\\d+.*");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
    
    
    // fourni, à compléter
    private MENU_VAL menuJeu() throws IOException, SAXException, TransformerException, TransformerConfigurationException, ParserConfigurationException {

        MENU_VAL playTheGame;
        playTheGame = MENU_VAL.MENU_JOUE;
        Partie partie;
        
        // Récupération de la date du jour
        LocalDate currentDate = LocalDate.now();
        // Formateur de date personnalisé
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = currentDate.format(formatter);
        
        // Lecture DOM du dictionnaire depuis le fichier dico.xml
        dico.lireDictionnaireDOM("./src/xml", "dico.xml");
        
        // Lecture SAX du dictionnaire depuis le fichier dico.xml
        //dico.lireDictionnaire();
        
        do {
            // restaure la room du menu
            env.setRoom(menuRoom);
            
            // affiche menu
            menuText.getText("Question").display();
            menuText.getText("Jeu1").display();
            menuText.getText("Jeu2").display();
            menuText.getText("Jeu3").display();
            menuText.getText("Jeu4").display();
            menuText.getText("Jeu5").display();
            
            
            // vérifie qu'une touche 1, 2, 3, 4 ou 5 est pressée
            int touche = 0;
            while (!(touche == Keyboard.KEY_1 || 
                    touche == Keyboard.KEY_2 || 
                    touche == Keyboard.KEY_3 || 
                    touche == Keyboard.KEY_4 ||
                    touche == Keyboard.KEY_5))
            {
                touche = env.getKey();
                env.advanceOneFrame();
            }

            // nettoie l'environnement du texte
            menuText.getText("Question").clean();
            menuText.getText("Jeu1").clean();
            menuText.getText("Jeu2").clean();
            menuText.getText("Jeu3").clean();
            menuText.getText("Jeu4").clean();
            menuText.getText("Jeu5").clean();


            // et décide quoi faire en fonction de la touche pressée
            switch (touche) {
                // -----------------------------------------
                // Touche 1 : Commencer une nouvelle partie
                // -----------------------------------------                
                case Keyboard.KEY_1: // choisi un niveau et charge un mot depuis le dico
                    
                    // niveau choisi
                    int niveau = 0 ;
                    
                    do{
                        niveau = Integer.parseInt(getNiveauPartie()) ;
                    }while(niveau > 5 || niveau < 1) ;
                    
                    // .......... dico.******
                    // récupération d'un mot depuis le dico
                    String mot ;
                    mot = dico.getMotDepuisListeNiveaux(niveau) ;
                    
                    // crée une nouvelle partie
                    partie = new Partie(date,mot,niveau);
                    
                    // mis à jour du mot à trouver
                    this.motATrouver = mot ;
                    menuText.addText("Trouve ce mot : " + motATrouver, "TrouveMot", 200, 300);
                    
                    // Affiche Trouve ce mot : xxxxxxxxx
                    getTrouveMot() ;
                    
                    // Met le programme principal en pause pendant 5 secondes
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                    // restaure la room du Jeu
                    env.setRoom(mainRoom);
                    
                    // joue
                    joue(partie);
                    
                    // enregistre la partie dans le profil --> enregistre le profil
                    // .......... profil.******
                    profil.ajoutePartie(partie) ;
                    
                    playTheGame = MENU_VAL.MENU_JOUE;
                    break;

                // -----------------------------------------
                // Touche 2 : Charger une partie existante
                // -----------------------------------------                
                case Keyboard.KEY_2: // charge une partie existante
                    
                    // On vérifie que le joueur a une partie existante en lui demandant le mot de la partie 
                    String motPartie = getMotPartie() ;
                    
                    // On vérifie si la partie existe
                    int indice = profil.chargePartie(motPartie) ;   // indice de la partie demandée
                    if(indice != -1){
                        // Récupération de la partie correspondant à cet indice
                        partie = profil.getParties().get(indice) ;
                        partie.setDate(date);
                    }
                    else{
                        // partie demandée non existante
                        playTheGame = MENU_VAL.MENU_JOUE ;
                        break ; 
                    }
                    
                    // Recupère le mot de la partie
                    motPartie = partie.getMot() ;
                    
                    // Ce mot est le mot que le joueur doit trouver
                    this.motATrouver = motPartie ;
                    menuText.addText("Trouve ce mot : " + motATrouver, "TrouveMot", 200, 300);
                    
                    // Affiche Trouve ce mot : xxxxxxxxx
                    getTrouveMot() ;
                    
                    // Met le programme principal en pause pendant 5 secondes
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                    // restaure la room du Jeu
                    env.setRoom(mainRoom);
                    
                    // joue
                    joue(partie);
                    // enregistre la partie dans le profil --> enregistre le profil
                    // .......... profil.******
                    profil.ajoutePartie(partie) ;
                    playTheGame = MENU_VAL.MENU_JOUE;
                    break;

                // -----------------------------------------
                // Touche 3 : Ajoute une nouvelle partie
                // -----------------------------------------                
                case Keyboard.KEY_3:
                    
                    // Demande au joueur de resaisir le mot s'il n'est pas valide
                    do{
                        // Mot de la nouvelle partie
                        mot = getMotPartie() ;
                    } while(contientEntier(mot)) ;
                    
                    // Niveau de la nouvelle partie
                    do{
                        niveau = Integer.parseInt(getNiveauPartie()) ;
                    }while(niveau > 5 || niveau < 1) ;
                    
                    // Ajout du mot au document DOM
                    dico.ajouterMot(mot, niveau) ;
                    
                    // Ajout du mot à la liste des mots
                    dico.ajouteMotADico(niveau, mot);
                    
                    // Creation de la nouvelle partie
                    partie = new Partie(date,mot,niveau) ;
                    
                    // On ajoute la partie créée au joueur
                    profil.ajoutePartie(partie);
                    
                    // Ce mot est le mot que le joueur doit trouver
                    this.motATrouver = mot ;
                    menuText.addText("Trouve ce mot : " + motATrouver, "TrouveMot", 200, 300);
                    
                    // Affiche Trouve ce mot : xxxxxxxxx
                    getTrouveMot() ;
                    
                    // Met le programme principal en pause pendant 5 secondes
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                    // restaure la room du Jeu
                    env.setRoom(mainRoom);
                    
                    // Lance la partie
                    joue(partie) ;
                    
                    playTheGame = MENU_VAL.MENU_JOUE ;
                    break ;
                    
                // -----------------------------------------
                // Touche 4 : Sortie de ce jeu
                // -----------------------------------------                
                case Keyboard.KEY_4:
                    
                    // On demande au joueur s'il veut imprimer ses parties
                    int reponse = Integer.parseInt(getImprimeParties());
                    
                    if(reponse == 1){
                        // Le joueur veut imprimer les parties jouées
                        
                        // Impression console
                        System.out.println("\n" + profil.toString()) ;
                        
                        // Ecriture dans un fichier xml
                        if(newPlayer){
                            // Sauvegarde d'un nouveau joueur
                            profil.sauvegarderNewProfil("./src/xml/" + profil.getNom() + ".xml");
                        }
                        else{
                            // Ancien joueur, la sauvegard ajoute juste les nouvelles parties
                            profil.sauvegarder("./src/xml/" + profil.getNom() + ".xml");
                        }
                        
                        playTheGame = MENU_VAL.MENU_CONTINUE;
                    }
                    else{
                        playTheGame = MENU_VAL.MENU_CONTINUE;
                    }
                    break;
                // -----------------------------------------
                // Touche 5 : Quitter le jeu
                // -----------------------------------------
                case Keyboard.KEY_5:
                    playTheGame = MENU_VAL.MENU_SORTIE;
            }
        } while (playTheGame == MENU_VAL.MENU_JOUE);
        return playTheGame;
    }

    private MENU_VAL menuPrincipal() throws IOException, SAXException, TransformerException, TransformerConfigurationException, ParserConfigurationException {

        MENU_VAL choix = MENU_VAL.MENU_CONTINUE;
        String nomJoueur;
        String date;
        String avatar ;

        // restaure la room du menu
        env.setRoom(menuRoom);

        menuText.getText("Question").display();
        menuText.getText("Principal1").display();
        menuText.getText("Principal2").display();
        menuText.getText("Principal3").display();
               
        // vérifie qu'une touche 1, 2 ou 3 est pressée
        int touche = 0;
        while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3)) {
            touche = env.getKey();
            env.advanceOneFrame();
        }

        menuText.getText("Question").clean();
        menuText.getText("Principal1").clean();
        menuText.getText("Principal2").clean();
        menuText.getText("Principal3").clean();

        // et décide quoi faire en fonction de la touche pressée
        switch (touche) {
            // -------------------------------------
            // Touche 1 : Charger un profil existant
            // -------------------------------------
            case Keyboard.KEY_1:
                // demande le nom du joueur existant
                nomJoueur = getNomJoueur();
                // charge le profil de ce joueur si possible
                if(profil.charge(nomJoueur)) {
                    choix = menuJeu();
                } else {
                    choix = MENU_VAL.MENU_CONTINUE ; //SORTIE ;
                }
                break;

            // -------------------------------------
            // Touche 2 : Créer un nouveau joueur
            // -------------------------------------
            case Keyboard.KEY_2:
                // Met à true l'attribut newPlayer
                this.newPlayer = true ;
                // demande le nom du nouveau joueur
                nomJoueur = getNomJoueur();
                // demande la date de naissance du joueur
                date = getDateNaissance() ;
                // demande l'avatar du joueur
                avatar = getAvatar() ;
                avatar += ".png" ;
                // crée un profil avec le nom, la date et l'avatar d'un nouveau joueur
                profil = new Profil(nomJoueur,date,avatar);
                choix = menuJeu();
                break;

            // -------------------------------------
            // Touche 3 : Sortir du jeu
            // -------------------------------------
            case Keyboard.KEY_3:
                choix = MENU_VAL.MENU_SORTIE;
        }
        return choix;
    }
    
    // Vérifie la position d'une lettre par rapport aux autres
    private boolean checkPosition(double x, double z){
        for(int j=0; j<this.letters.size(); j++){
            if(this.letters.get(j).getX() == x || this.letters.get(j).getZ() == z ){
                return true ;
            }
        }
        return false ;
    }

    public void joue(Partie partie) throws IOException, SAXException {
        partie.setFini(false);
        // Instancie un Tux
        tux = new Tux(env, mainRoom);
        env.addObject(tux);
        
        // Reinitialise l'arrayList letters
        letters = new ArrayList<Letter>();
        
        String motPartie = partie.getMot() ;
        
        /////// Rajout des lettres du mot dans l'arrayliste letters ///////
        Random random = new Random() ;
        int x = random.nextInt(100) ;    // x pour la position des lettres
        int z = random.nextInt(100) ;    // z pour la position des lettres
        char c ;        // un caractère du mot
        for(int i=0; i<motPartie.length(); i++){
            c = motPartie.charAt(i) ;  // On choisit un caractère
            Letter letter = new Letter(c,x,5.0,z);    // L'objet correspondant au caractère choisi au hasard
            letters.add(letter);    // On rajoute le caractère dans la liste des lettres
            x = random.nextInt(100) ;  // on met à jour la position du prochain caractère
            z = random.nextInt(100) ;
            while(checkPosition(x,z)){
                x = random.nextInt(100) ;
                z = random.nextInt(100) ;
            }
        }
        
        // Affichage des lettres contenues dans la Liste lettresAleatoire dans l'environnement
        for(Letter l : letters){
            env.addObject(l);
        }

        // Ici, on peut initialiser des valeurs pour une nouvelle partie
        demarrePartie(partie);

        // Boucle de jeu
        Boolean finished;
        finished = false;
        while (!finished) {

            // Contrôles globaux du jeu (sortie, ...)
            //1 is for escape key
            if (env.getKey() == 1 || partie.isFini()) {
                finished = true;
            }

            // Contrôles des déplacements de Tux (gauche, droite, ...)
            tux.deplace();

            // Ici, on applique les regles
            appliqueRegles(partie);

            // Fait avancer le moteur de jeu (mise à jour de l'affichage, de l'écoute des événements clavier...)
            env.advanceOneFrame();
        }
        
        terminePartie(partie) ;
    }

    protected abstract void demarrePartie(Partie partie);

    protected abstract void appliqueRegles(Partie partie);

    protected abstract void terminePartie(Partie partie);
    
    // renvoie la distance du personnage tux à la lettre
    public double distance(Letter letter){
        double dist ;
        double diffX = letter.getX() - tux.getX() ;
        double diffY = letter.getY() - tux.getY() ;
        double diffZ = letter.getZ() - tux.getZ() ;
        dist = Math.sqrt(Math.pow(diffX,2) + Math.pow(diffY,2) + Math.pow(diffZ,2)) ;
        return dist ;
    }
    
    // renvoie true si le personnage et la lettre sont en collision
    public boolean collision(Letter letter){
        //double tolerance = 1.0; // Valeur de tolérance pour les comparaisons avec des nombres flottants
        double dist = distance(letter) ;
        return dist <= (tux.getScale() * 4.0) / 2.0 ;
    }

}
