package invadem;

import processing.core.PImage;

public class Projectile implements Drawable, Collidable {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private boolean friendly;
    private boolean hit;

    private PImage imgProjectile;

    public Projectile(PImage imgProjectile, int posX, int posY, boolean friendly) {
        this.imgProjectile = imgProjectile;

        this.posX = posX;
        this.posY = posY;
        this.width = 1;
        this.height = 3;
        this.friendly = friendly;
        this.hit = false;
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

    public boolean isFriendly() {
        return this.friendly;
    }

    public boolean isDestroyed() {
        return this.hit;
    }

    public void hit() {
        this.hit = true;
    }

    private void tick() {
        this.posY -= 1;
    }

    public void draw(App app) {
        app.image(this.imgProjectile, this.posX, this.posY, this.width, this.height);
        tick();
    }
}
