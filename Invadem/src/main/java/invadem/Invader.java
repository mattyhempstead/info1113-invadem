package invadem;

import processing.core.PImage;

public class Invader implements Drawable, Collidable {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private boolean destroyed;
    private int stateNum;
    private int stateTick;

    private PImage[] imgInvader;
    private PImage imgProjectile;
    
    public Invader(PImage[] imgInvader, PImage imgProjectile, int posX, int posY) {
        this.imgInvader = imgInvader;
        this.imgProjectile = imgProjectile;

        this.destroyed = false;
        
        // Used to keep track of the animation cycle
        this.stateNum = 0;  // The current state of the Invader animation (0=right, 1=down, 2=left, 3=down)
        this.stateTick = 30; // The number of ticks until the next state

        this.width = 16;
        this.height = 16;
        this.posX = posX;
        this.posY = posY;
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
     * Returns a new invader projectile located at the center of the invader
     * @return The projectile instance
     */
    public Projectile fire() {
        return new Projectile(
            this.imgProjectile,
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
            this.imgInvader[this.stateNum % 2], 
            this.posX, 
            this.posY,
            this.width, 
            this.height
        );
        tick();
    }
}
