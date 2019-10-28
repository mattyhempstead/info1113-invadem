package invadem;

import java.util.ArrayList;
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
    private PImage[] imgInvader;


    public void setup() {
        frameRate(60);
        // rectMode(CENTER);   // Draw all rectangles from the center




        // Maybe pass in the image sprites to the constructors
        // Maybe load the images here and then pass the image objects in the draw method
        // Tank and Enemy should be given images for themselves, and also projectiles which they shoot
        this.imgTank = loadImage("tank1.png");
        this.imgProjectile = loadImage("projectile.png");
        this.imgInvader = new PImage[] { loadImage("invader1.png"), loadImage("invader2.png") };

        Barrier.loadResources(
            new PImage[] { loadImage("barrier_left1.png"), loadImage("barrier_left2.png"), loadImage("barrier_left3.png") },
            new PImage[] { loadImage("barrier_right1.png"), loadImage("barrier_right2.png"), loadImage("barrier_right3.png") },
            new PImage[] { loadImage("barrier_solid1.png"), loadImage("barrier_solid2.png"), loadImage("barrier_solid3.png") },
            new PImage[] { loadImage("barrier_top1.png"), loadImage("barrier_top2.png"), loadImage("barrier_top3.png") }
        );

        this.tank = new Tank(this.imgTank, this.imgProjectile);
        this.movingLeft = false;
        this.movingRight = false;

        this.projectiles = new ArrayList<Projectile>();

        
        this.invaders = new ArrayList<Invader>();

        for (int i=0; i<40; i++) {
            this.invaders.add(new Invader(
                this.imgInvader, 
                this.imgProjectile, 
                320 - 9 + (i%10 - 5) * 28, 
                50 + (i/10) * 32
            ));
        }

        // Create barriers
        this.barriers = new ArrayList<Barrier>();
        Barrier.resetBarriers(this.barriers);


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

        // If invader shoot countdown has finished, add a new invader projectile to the projectile list
        Invader.tickShootCountdown();
        if (Invader.shouldInvadersShoot()) {
            this.projectiles.add(Invader.shootFromInvader(this.invaders));
        }


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

            // Check invader collision from friendly projectiles
            if (proj.isFriendly()) {
                for (Invader invader: this.invaders) {
                    if (Collidable.isColliding(proj, invader)) {
                        proj.hit();
                        invader.hit();
                    }
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
