package invadem;

import processing.core.PImage;

public class Barrier implements Drawable {
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
    
    private void tick() {

    }

    public void draw(App app) {
        app.image(
            this.imgBarrierArray[health - 3], 
            this.posX,
            this.posY,
            this.width, 
            this.height
        );
        tick();
    }
}
