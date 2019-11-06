package invadem;

import java.util.ArrayList;
import processing.core.PImage;

public class Projectile implements Drawable, Collidable {
    private static ArrayList<Projectile> projectiles = new ArrayList<>();

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

    public static void resetProjectiles() {
        Projectile.projectiles.clear();
    }

    public static ArrayList<Projectile> getProjectiles() {
        return Projectile.projectiles;
    }

    public static void addProjectile(Projectile proj) {
        Projectile.projectiles.add(proj);
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


        /// Check if projectile has collided with any objects

        // Check barrier collision
        Barrier.checkProjectileCollision(this); 

        // Check invader collision from friendly projectiles
        Invader.checkProjectileCollision(this);

        // Check invader projectile collision with tank
        Tank.checkProjectileCollision(this); 

    }

    public static void drawProjectiles(App app) {
        // Draw and tick projectiles
        for (Projectile proj : Projectile.projectiles) {
            proj.draw(app);
        }
    }

    public void draw(App app) {
        app.image(imgProj, this.posX, this.posY, this.width, this.height);
        tick();
    }
}
