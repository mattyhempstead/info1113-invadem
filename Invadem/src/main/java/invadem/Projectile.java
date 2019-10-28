package invadem;

import processing.core.PImage;

public class Projectile implements Drawable, Collidable {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private boolean friendly;
    private boolean destroyed;

    private PImage imgProjectile;

    public Projectile(PImage imgProjectile, int posX, int posY, boolean friendly) {
        this.imgProjectile = imgProjectile;

        this.posX = posX;
        this.posY = posY;
        this.width = 1;
        this.height = 3;
        this.friendly = friendly;
        this.destroyed = false;
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
        return this.destroyed;
    }

    public void hit() {
        this.destroyed = true;
    }

    private void tick() {
        this.posY -= 1;
    }

    public void draw(App app) {
        app.image(this.imgProjectile, this.posX, this.posY, this.width, this.height);
        tick();
    }
}
