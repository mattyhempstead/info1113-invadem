package invadem;

import processing.core.PImage;

public class Barrier implements Drawable, Collidable {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private int health;

    private PImage[] imgBarrierArray;
    
    public Barrier(PImage[] imgBarrierArray, int posX, int posY) {
        this.imgBarrierArray = imgBarrierArray;

        this.health = 3;

        this.width = 8;
        this.height = 8;
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
        if (this.health > 0) {
            this.health--;
        }
    }

    public boolean isDestroyed() {
        return this.health == 0;
    }

    private void tick() {

    }

    public void draw(App app) {
        app.image(
            this.imgBarrierArray[3 - health], 
            this.posX,
            this.posY,
            this.width, 
            this.height
        );
        tick();
    }
}
