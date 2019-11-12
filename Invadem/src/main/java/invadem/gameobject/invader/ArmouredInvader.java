package invadem.gameobject.invader;

import invadem.App;

import processing.core.PImage;

public class ArmouredInvader extends Invader {
    // Static variables to store ArmouredInvader resources
    private static PImage[] imgArmouredInvader;

    public ArmouredInvader(int posX, int posY) {
        super(posX, posY);

        this.points = 250;

        // Override default health of 1 for ArmouredInvaders
        this.health = 3;

        // Override image resource reference for ArmouredInvader
        this.imgRef = ArmouredInvader.imgArmouredInvader;
    }

    /**
     * Load sprites statically into the ArmouredInvader class
     */
    public static void loadResources(App app) {
        ArmouredInvader.imgArmouredInvader = new PImage[] { 
            app.loadImage("invader1_armoured.png"),
            app.loadImage("invader2_armoured.png")
        };
    }
}
