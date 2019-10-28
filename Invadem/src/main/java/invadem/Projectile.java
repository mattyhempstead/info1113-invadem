package invadem;

import processing.core.PImage;

public class Projectile implements Drawable {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private boolean friendly;


    public Projectile(int posX, int posY, boolean friendly) {
        this.posX = posX;
        this.posY = posY;
        this.width = 1;
        this.height = 3;
        this.friendly = friendly;
    }

    private void tick() {
        this.posY -= 1;
    }

    public void draw(App app, PImage img) {
        app.image(img, this.posX, this.posY, this.width, this.height);
        tick();
    }
}
