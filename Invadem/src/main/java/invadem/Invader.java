package invadem;

import java.util.ArrayList;
import java.lang.Math;
import processing.core.PImage;

public class Invader implements Drawable, Collidable {
    private static ArrayList<Invader> invaders = new ArrayList<>();

    private static int tickCounter = 0;
    
    // Variables to keep track of when invader swarm should shoot
    private static int shootCountdownTickLength = 60 * 5;
    private static int shootCountdown = 0;
 
    // Store invader resources
    private static PImage[] imgInvader;
    
    private int posX;
    private int posY;
    private int width;
    private int height;
    private boolean destroyed;
    private int stateNum;
    private int stateTick;
   
    public Invader(int posX, int posY) {

        this.destroyed = false;
        
        // Used to keep track of the animation cycle
        this.stateNum = 0;  // The current state of the Invader animation (0=right, 1=down, 2=left, 3=down)
        this.stateTick = 30; // The number of ticks until the next state

        this.width = 16;
        this.height = 16;
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Load sprites statically into the Invader class
     */
    public static void loadResources(App app) {
        Invader.imgInvader = new PImage[] { app.loadImage("invader1.png"), app.loadImage("invader2.png") };
    }

    /**
     * Resets all invaders to their initial 4x10 grid state
     */
    public static void resetInvaders() {
        // Clear the barriers list before adding new ones
        Invader.invaders.clear();

        // Add 4x10 grid of invaders
        for (int i=0; i<40; i++) {
            Invader.invaders.add(new Invader(
                320 - 9 + (i%10 - 5) * 28, 
                50 + (i/10) * 32
            ));
        }
    }
    
    /**
     * Returns the list of invaders
     */
    public static ArrayList<Invader> getInvaders() {
        return Invader.invaders;
    }

    /**
     * Decrements the shoot countdown for invaders and resets when it reaches 0
     */
    public static void tickShootCountdown() {
        if (shootCountdown == 0) {
            shootCountdown = Invader.shootCountdownTickLength;
        } else {
            shootCountdown--;
        }
    }

    /**
     * Checks if the shootCountdown has finished
     * @return Whether or not invader swarm should shoot
     */
    public static boolean shouldInvadersShoot() {
        return shootCountdown == 0;
    }

    /**
     * Spawns a new invader projectile coming from a random invader
     * @return the projectile object
     */
    public static Projectile shootFromInvader() {
        int randomInvaderIndex = (int) (Math.random() * Invader.invaders.size());
        Invader randomInvader = Invader.invaders.get(randomInvaderIndex);
        return randomInvader.fire();
    }
 
    /**
     * Hit any invaders which are colliding with a particular projectile
     */
    public static void checkProjectileCollision(Projectile proj) {
        // Only collide invaders with friendly projectiles
        if (!proj.isFriendly()) return;
 
        for (Invader invader : Invader.invaders) {
            if (Collidable.isColliding(proj, invader)) {
                invader.hit();
                proj.hit();
            }
        }
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }

    public void hit() {
        this.destroyed = true;
    }

    public boolean isDestroyed() {
        return this.destroyed;
    }

    /**
     * Returns whether or not any invaders have reached the barriers
     * @return  Boolean of whether any invader has reacher barrier
     */
    public static boolean hasReachedBarriers() {
        for (Invader invader : Invader.invaders) {
            if (invader.getPosY() + invader.getHeight() >= 400 - 12) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a new invader projectile located at the center of the invader
     * @return The projectile instance
     */
    public Projectile fire() {
        return new Projectile(
            this.posX + (this.width / 2),
            this.posY + (this.height / 2),
            false
        );
    }

    public void tick() {
        this.stateTick--;
        
        // Move invader in a direction depending on its current state
        // States are cycled through on at an interval of 30 ticks horizontal and 8 ticks vertical
        switch (this.stateNum) {
            case 0:
                // Move right for 30 ticks
                this.posX++;
                if (this.stateTick == 0) {
                    this.stateNum = 1;
                    this.stateTick = 8;
                }
                break;
            case 1:
                // Move down for 8 ticks
                this.posY++;
                if (this.stateTick == 0) {
                    this.stateNum = 2;
                    this.stateTick = 30;
                }
                break;
            case 2:
                // Move left for 30 ticks
                this.posX--;
                if (this.stateTick == 0) {
                    this.stateNum = 3;
                    this.stateTick = 8;
                }
                break;
            case 3:
                // Move down for 8 ticks
                this.posY++;
                if (this.stateTick == 0) {
                    this.stateNum = 0;
                    this.stateTick = 30;
                }
                break;
            default:
                break;
        }

    }

    public static void drawInvaders(App app) {
        Invader.tickCounter++;

        // Draw all invaders
        for (Invader invader : Invader.invaders) {
            invader.draw(app);
        }
    }

    /**
     * Draws the instance invader to the screen
     * @param App   The app to draw the invader on 
     */
    public void draw(App app) {
        app.image(
            imgInvader[this.stateNum % 2], 
            this.posX, 
            this.posY,
            this.width, 
            this.height
        );

        // Move invaders every second frame
        if (Invader.tickCounter%2 == 0) tick();
    }
}

