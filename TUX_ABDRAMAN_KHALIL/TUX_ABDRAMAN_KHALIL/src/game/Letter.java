/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import env3d.advanced.EnvNode;

/**
 *
 * @author Khalil
 */
public class Letter extends EnvNode {
    
    // Les attributs
    private char letter ;

    // Getter de letter
    public char getLetter() {
        return letter;
    }
    
    // Le Constructeur
    public Letter(char l, double x, double y, double z){
        this.letter = l ;
        setScale(4.0);
        setX(x);
        setY(y);
        setZ(z);
        if(String.valueOf(letter).equals(" ")){
            setTexture("models/letter/cube.png");
        }
        else{
            setTexture("models/letter/" + l + ".png");
        }
        setModel("models/letter/cube.obj");
    }

}
