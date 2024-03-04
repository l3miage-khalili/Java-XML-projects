/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author Khalil
 */
public class Room {
    // Les attributs
    private int depth ;
    private int height ;
    private int width ;
    private String textureBottom ;
    private String textureNorth ;
    private String textureEast ;
    private String textureWest ;
    private String textureTop ;
    private String textureSouth ;
    
    public Document _doc;
    
    
    // Les Opérations
    public Room() throws ParserConfigurationException, SAXException, IOException {
        
        // Parse du document plateau.xml
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance() ;
        DocumentBuilder builder = factory.newDocumentBuilder() ;
        this._doc = builder.parse("./src/xml/plateau.xml") ;
        
        // Récupération du height
        Element heightElt = (Element) this._doc.getElementsByTagName("height").item(0) ;
        this.height = Integer.parseInt((heightElt.getTextContent())) ;
        
        // Récupération du width
        Element widthElt = (Element) this._doc.getElementsByTagName("width").item(0) ;
        this.width = Integer.parseInt((widthElt.getTextContent())) ;
        
        // Récupération du depth
        Element depthElt = (Element) this._doc.getElementsByTagName("depth").item(0) ;
        this.depth = Integer.parseInt((depthElt.getTextContent())) ;
        
        // Récupération du textureBottom
        Element bottomElt = (Element) this._doc.getElementsByTagName("textureBottom").item(0) ;
        this.textureBottom = bottomElt.getTextContent() ;
        
        // Récupération du textureEast
        Element eastElt = (Element) this._doc.getElementsByTagName("textureEast").item(0) ;
        this.textureEast = eastElt.getTextContent() ;
        
        // Récupération du textureWest
        Element westElt = (Element) this._doc.getElementsByTagName("textureWest").item(0) ;
        this.textureWest = westElt.getTextContent() ;
        
        // Récupération du textureNorth
        Element northElt = (Element) this._doc.getElementsByTagName("textureNorth").item(0) ;
        this.textureNorth = northElt.getTextContent() ;
        
        textureTop = null ;
        textureSouth = null ;
    }
    
    // Getters and Setters

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getTextureBottom() {
        return textureBottom;
    }

    public void setTextureBottom(String textureBottom) {
        this.textureBottom = textureBottom;
    }

    public String getTextureNorth() {
        return textureNorth;
    }

    public void setTextureNorth(String textureNorth) {
        this.textureNorth = textureNorth;
    }

    public String getTextureEast() {
        return textureEast;
    }

    public void setTextureEast(String textureEast) {
        this.textureEast = textureEast;
    }

    public String getTextureWest() {
        return textureWest;
    }

    public void setTextureWest(String textureWest) {
        this.textureWest = textureWest;
    }

    public String getTextureTop() {
        return textureTop;
    }

    public void setTextureTop(String textureTop) {
        this.textureTop = textureTop;
    }

    public String getTextureSouth() {
        return textureSouth;
    }

    public void setTextureSouth(String textureSouth) {
        this.textureSouth = textureSouth;
    }
    
    
    
}
