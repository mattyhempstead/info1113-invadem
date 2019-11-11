package invadem;

import java.util.ArrayList;
import processing.core.PImage;

public class Projectile extends Entity {
    private static ArrayList<Projectile> projectiles = new ArrayList<>();

    private boolean friendly;

    private static PImage imgProj;

    public Projectile(int posX, int posY, boolean friendly) {
        this.posX = posX;
        this.posY = posY;
        this.width = 1;
        this.height = 3;
        this.friendly = friendly;
    }

    /**
     * Load sprites statically into the Projectile class
     */
    public static void loadResources(App app) {
        Projectile.imgProj = app.loadImage("projectile.png");
    }

    /**
     * Reset projectiles for a new level
     * This involves simply clearing the List of projectiles
     */
    public static void resetProjectiles() {
        Projectile.projectiles.clear();
    }

    public static ArrayList<Projectile> getProjectiles() {
        return Projectile.projectiles;
    }

    public static void addProjectile(Projectile proj) {
        Projectile.projectiles.add(proj);
    }

    public boolean isFriendly() {
        return this.friendly;
    }

    private void tick(App app) {
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
            proj.tick(app);
        }
    }

    public void draw(App app) {
        app.image(imgProj, this.posX, this.posY, this.width, this.height);
    }
}
