package invadem;

import processing.core.PImage;

public class Projectile implements Drawable, Collidable {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private boolean friendly;
    private boolean destroyed;

    private static PImage imgProj;

    public Projectile(int posX, int posY, boolean friendly) {
        this.posX = posX;
        this.posY = posY;
        this.width = 1;
        this.height = 3;
        this.friendly = friendly;
        this.destroyed = false;
    }

    /**
     * Load sprites statically into the Projectile class
     */
    public static void loadResources(App app) {
        Projectile.imgProj = app.loadImage("projectile.png");
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
        this.posY += this.friendly ? -1 : 1;

        // Destroy if projectile leaves map
        if (this.posY < 0 || this.posY > 480) {
            this.destroyed = true;
        }
    }

    public void draw(App app) {
        app.image(imgProj, this.posX, this.posY, this.width, this.height);
        tick();
    }
}
