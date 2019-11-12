package invadem.gameobject;

import invadem.App;
import invadem.Collidable;
import invadem.gameobject.projectile.Projectile;

import java.util.ArrayList;
import processing.core.PImage;

public class Barrier extends Entity {
    private static ArrayList<Barrier> barriers = new ArrayList<>();

    private static PImage[] imgLeft;
    private static PImage[] imgRight;
    private static PImage[] imgSolid;
    private static PImage[] imgTop;

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
     */
    public static void loadResources(App app) {
        Barrier.imgLeft =  new PImage[] { app.loadImage("barrier_left1.png"),  app.loadImage("barrier_left2.png"),  app.loadImage("barrier_left3.png")  };
        Barrier.imgRight = new PImage[] { app.loadImage("barrier_right1.png"), app.loadImage("barrier_right2.png"), app.loadImage("barrier_right3.png") };
        Barrier.imgSolid = new PImage[] { app.loadImage("barrier_solid1.png"), app.loadImage("barrier_solid2.png"), app.loadImage("barrier_solid3.png") };
        Barrier.imgTop =   new PImage[] { app.loadImage("barrier_top1.png"),   app.loadImage("barrier_top2.png"),   app.loadImage("barrier_top3.png")   };
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
                proj.hit(barrier);
            }
        }
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
