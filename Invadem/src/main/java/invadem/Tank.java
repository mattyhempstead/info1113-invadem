package invadem;

import processing.core.PImage;

public class Tank implements Drawable, Collidable {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private int health;

    private PImage imgTank;
    private PImage imgProjectile;
    
    public Tank(PImage imgTank, PImage imgProjectile) {
        this.imgTank = imgTank;
        this.imgProjectile = imgProjectile;

        this.health = 3;

        this.width = 22;
        this.height = 16;
        this.posX = (640 / 2) - (this.width / 2);
        this.posY = 480 - this.height - 10;
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

    public void moveLeft() {
        if (this.posX > 0) {
            this.posX -= 1;
        }
    }

    public void moveRight() {
        if (this.posX < 640) {
            this.posX += 1;
        }
    }

    public void hit() {
        if (this.health > 0) {
            this.health--;
        }
    }

    public boolean isDestroyed() {
        return this.health == 3;
    }

    /**
     * Returns a newly spawned tank projectile at the location of tank
     * @return The projectile instance
     */
    public Projectile fire() {
        return new Projectile(
            this.imgProjectile,
            this.posX + (this.width / 2),
            this.posY,
            true
        );
    }

    public void draw(App app) {
        // app.rect(this.posX, this.posY, this.width, this.height);
        app.image(
            this.imgTank, 
            this.posX, 
            this.posY,
            this.width, 
            this.height
        );
    }
}
