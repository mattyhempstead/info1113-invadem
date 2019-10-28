package invadem;

import java.util.ArrayList;
import java.lang.Math;
import processing.core.PImage;

public class Invader implements Drawable, Collidable {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private boolean destroyed;
    private int stateNum;
    private int stateTick;

    // Variables to keep track of when invader swarm should shoot
    private final static int MIN_INVADER_SHOOT_TICKS = 60;
    private final static int MAX_INVADER_SHOOT_TICKS = 60 * 3;
    private static int shootCountdown = 0;

    // Store invader resources
    private static PImage[] imgInvader;
    
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
     * @param imgInvader   Array of both invader sprites
     */
    public static void loadResources(PImage[] imgInvader) {
        Invader.imgInvader = imgInvader;
    }

    /**
     * Resets all invaders to their initial 4x10 grid state
     * @param invaders a reference to the ArrayList of invaders
     */
    public static void resetInvaders(ArrayList<Invader> invaders) {
        // Clear the barriers list before adding new ones
        invaders.clear();

        // Add 4x10 grid of invaders
        for (int i=0; i<40; i++) {
            invaders.add(new Invader(
                320 - 9 + (i%10 - 5) * 28, 
                50 + (i/10) * 32
            ));
        }
    }

    /**
     * Decrements the shoot countdown for invaders and resets when it reaches 0
     * Will reset to a random integer between MIN_INVADER_SHOOT_TICKS and MAX_INVADER_SHOOT_TICKS
     */
    public static void tickShootCountdown() {
        if (shootCountdown == 0) {
            shootCountdown = (int) (Math.random() * (MAX_INVADER_SHOOT_TICKS - MIN_INVADER_SHOOT_TICKS) + MIN_INVADER_SHOOT_TICKS);
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
     * Spawns a new invader projectile coming from a random invader in a list
     * @return the projectile object
     */
    public static Projectile shootFromInvader(ArrayList<Invader> invaders) {
        Invader randomInvader = invaders.get((int) (Math.random() * invaders.size()));
        return randomInvader.fire();
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

    public boolean hasReachedBarriers() {
        return this.posY + this.height >= 400 - 12;
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

    public void draw(App app) {
        app.image(
            imgInvader[this.stateNum % 2], 
            this.posX, 
            this.posY,
            this.width, 
            this.height
        );
        tick();
    }
}
