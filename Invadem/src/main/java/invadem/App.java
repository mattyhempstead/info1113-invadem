package invadem;

import java.util.ArrayList;
import java.util.Arrays;
import processing.core.PApplet;
import processing.core.PImage;

public class App extends PApplet {
    private Tank tank;
    private ArrayList<Projectile> projectiles;
    private ArrayList<Barrier> barriers;
    private ArrayList<Invader> invaders;
    
    private boolean movingLeft;
    private boolean movingRight;

    // Load resources
    private PImage imgTank;
    private PImage imgProjectile;
    private PImage[] imgBarrierLeft;
    private PImage[] imgBarrierRight;
    private PImage[] imgBarrierSolid;
    private PImage[] imgBarrierTop;
    private PImage[] imgInvader;


    public void setup() {
        frameRate(60);
        // rectMode(CENTER);   // Draw all rectangles from the center

        // Maybe pass in the image sprites to the constructors
        // Maybe load the images here and then pass the image objects in the draw method
        // Tank and Enemy should be given images for themselves, and also projectiles which they shoot
        this.imgTank = loadImage("tank1.png");
        this.imgProjectile = loadImage("projectile.png");
        this.imgBarrierLeft = new PImage[] { loadImage("barrier_left1.png"), loadImage("barrier_left2.png"), loadImage("barrier_left3.png") };
        this.imgBarrierRight = new PImage[] { loadImage("barrier_right1.png"), loadImage("barrier_right2.png"), loadImage("barrier_right3.png") };
        this.imgBarrierSolid = new PImage[] { loadImage("barrier_solid1.png"), loadImage("barrier_solid2.png"), loadImage("barrier_solid3.png") };
        this.imgBarrierTop = new PImage[] { loadImage("barrier_top1.png"), loadImage("barrier_top2.png"), loadImage("barrier_top3.png") };
        this.imgInvader = new PImage[] { loadImage("invader1.png"), loadImage("invader2.png") };


        this.projectiles = new ArrayList<Projectile>();
        this.tank = new Tank(this.imgTank, this.imgProjectile);

        this.invaders = new ArrayList<Invader>();
        this.invaders.add(new Invader(this.imgInvader, this.imgProjectile, 200, 50));


        // Create barriers
        this.barriers = new ArrayList<Barrier>(
            Arrays.asList(new Barrier[] {
                new Barrier(this.imgBarrierSolid, 320 - 12, 240 + 4),
                new Barrier(this.imgBarrierSolid, 320 - 12, 240 - 4),
                new Barrier(this.imgBarrierLeft, 320 - 12, 240 - 12),
                new Barrier(this.imgBarrierTop, 320 - 4, 240 - 12),
                new Barrier(this.imgBarrierRight, 320 + 4, 240 - 12),
                new Barrier(this.imgBarrierSolid, 320 + 4, 240 - 4),
                new Barrier(this.imgBarrierSolid, 320 + 4, 240 + 4)
            })
        );


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

        if (this.movingLeft) this.tank.moveLeft();
        if (this.movingRight) this.tank.moveRight();



        for (Projectile proj : this.projectiles) {
            proj.draw(this);


            // Check if projectile has collided with any objects
            // We are allowing projectiles to collide with two objects at once,
            // assuming both objects are simultaneously colliding with the projectile.

            // Check barrier collision
            for (Barrier barrier : this.barriers) {
                if (Collidable.isColliding(proj, barrier)) {
                    proj.hit();
                    barrier.hit();
                }
            }

            // Check invader collision
            for (Invader invader: this.invaders) {
                if (Collidable.isColliding(proj, invader)) {
                    proj.hit();
                    invader.hit();
                }
            }


            // if (Collidable.isColliding(proj, this.tank)) {
            //     proj.hit();
            //     this.tank.hit();
            // }


        }

        // Remove any projectiles/barriers/invaders which are destroyed
        this.projectiles.removeIf(proj -> proj.isDestroyed());
        this.barriers.removeIf(barrier -> barrier.isDestroyed());
        this.invaders.removeIf(invader -> invader.isDestroyed());


        for (Barrier barrier : this.barriers) {
            barrier.draw(this);
        }

        for (Invader invader : this.invaders) {
            invader.draw(this);
        }

        tank.draw(this);


    }

    public void keyPressed() {
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
