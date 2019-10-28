package invadem;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;

public class App extends PApplet {
    private ArrayList<Projectile> projectiles;
    
    private Tank tank;
    
    private boolean movingLeft;
    private boolean movingRight;

    // Load resources
    private PImage imgTank;
    private PImage imgProjectile;

    public void setup() {
        frameRate(60);
        // rectMode(CENTER);   // Draw all rectangles from the center

        // Maybe pass in the image sprites to the constructors
        // Maybe load the images here and then pass the image objects in the draw method
        // Tank and Enemy should be given images for themselves, and also projectiles which they shoot
        this.imgTank = this.loadImage("tank1.png");
        this.imgProjectile = this.loadImage("projectile.png");


        this.projectiles = new ArrayList<Projectile>();

        this.tank = new Tank(this.imgTank, this.imgProjectile);

        this.movingLeft = false;
        this.movingRight = false;

    }

    public void settings() {
        size(640, 480);
    }

    public void draw() { 
        //Main Game Loop
        background(0);

        // System.out.println("test");

        if (this.movingLeft) tank.moveLeft();
        if (this.movingRight) tank.moveRight();



        for (Projectile proj : this.projectiles) {
            proj.draw(this);
        }

        tank.draw(this);


    }

    public void keyPressed() {
        System.out.println(keyCode);
        switch (keyCode) {
            case 37:
                this.movingLeft = true;
                break;
            case 39:
                this.movingRight = true;
                break;
            case 32:
                this.projectiles.add(tank.fire());
                break;
            default:
                break;
        }
    }

    public void keyReleased() {
        // System.out.println("released - " + keyCode);
        switch (keyCode) {
            case 37:
                this.movingLeft = false;
                break;
            case 39:
                this.movingRight = false;
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {
        PApplet.main("invadem.App");
    }

}
