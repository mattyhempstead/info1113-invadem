package invadem;

import processing.core.PImage;

public class Projectile implements Drawable {
    
    private int posX;
    private int posY;
    private int width;
    private int height;
    private boolean friendly;

    private PImage imgProjectile;

    public Projectile(PImage imgProjectile, int posX, int posY, boolean friendly) {
        this.imgProjectile = imgProjectile;

        this.posX = posX;
        this.posY = posY;
        this.width = 1;
        this.height = 3;
        this.friendly = friendly;
    }

    private void tick() {
        this.posY -= 1;
    }

    public void draw(App app) {
        app.image(this.imgProjectile, this.posX, this.posY, this.width, this.height);
        tick();
    }
}
