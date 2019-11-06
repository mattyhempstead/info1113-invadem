package invadem;

import java.util.ArrayList;
import processing.core.PImage;

public class Barrier implements Drawable, Collidable {
    private static ArrayList<Barrier> barriers = new ArrayList<>();

    private static PImage[] imgLeft;
    private static PImage[] imgRight;
    private static PImage[] imgSolid;
    private static PImage[] imgTop;

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

    /**
     * Loads sprites statically into the barrier class
     * @param imgLeft   Array of left barrier sprites
     * @param imgRight  Array of right barrier sprites
     * @param imgSolid  Array of solid barrier sprites
     * @param imgTop    Array of top barrier sprites
     */
    public static void loadResources(PImage[] imgLeft, PImage[] imgRight, PImage[] imgSolid, PImage[] imgTop) {
        Barrier.imgLeft = imgLeft;
        Barrier.imgRight = imgRight;
        Barrier.imgSolid = imgSolid;
        Barrier.imgTop = imgTop;
    }

    /**
     * Resets all barriers to their initial state
     * @param barriers a reference to the ArrayList of barriers
     */
    public static void resetBarriers() {
        // Clear the barriers list before adding new ones
        Barrier.barriers.clear();

        // Add a 3 full barriers spread across the bottom
        for (int i=-1; i<=1; i++) {
            Barrier.barriers.add(new Barrier(imgSolid,  320 + i*100 - 12,   400 + 4));
            Barrier.barriers.add(new Barrier(imgSolid,  320 + i*100 - 12,   400 - 4));
            Barrier.barriers.add(new Barrier(imgLeft,   320 + i*100 - 12,   400 - 12));
            Barrier.barriers.add(new Barrier(imgTop,    320 + i*100 - 4,    400 - 12));
            Barrier.barriers.add(new Barrier(imgRight,  320 + i*100 + 4,    400 - 12));
            Barrier.barriers.add(new Barrier(imgSolid,  320 + i*100 + 4,    400 - 4));
            Barrier.barriers.add(new Barrier(imgSolid,  320 + i*100 + 4,    400 + 4));
        }

    }

    public static ArrayList<Barrier> getBarriers() {
        return Barrier.barriers;
    } 

    public static void checkProjectileCollision(Projectile proj) {
        for (Barrier barrier : Barrier.barriers) {
            if (Collidable.isColliding(proj, barrier)) {
                proj.hit();
                barrier.hit();
            }
        }
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

    public static void drawBarriers(App app) {
        for (Barrier barrier : Barrier.barriers) {
            barrier.draw(app);
        }
    }

    public void draw(App app) {
        app.image(
            this.imgBarrierArray[3 - health], 
            this.posX,
            this.posY,
            this.width, 
            this.height
        );
    }
}

